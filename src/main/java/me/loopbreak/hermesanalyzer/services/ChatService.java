package me.loopbreak.hermesanalyzer.services;

import me.loopbreak.hermesanalyzer.entity.ChatEntity;
import me.loopbreak.hermesanalyzer.entity.IntentInstanceEntity;
import me.loopbreak.hermesanalyzer.entity.ModelSettingsEntity;
import me.loopbreak.hermesanalyzer.entity.messages.AIMessageEntity;
import me.loopbreak.hermesanalyzer.entity.messages.MessageEntity;
import me.loopbreak.hermesanalyzer.entity.messages.PromptIterationEntity;
import me.loopbreak.hermesanalyzer.entity.messages.UserMessageEntity;
import me.loopbreak.hermesanalyzer.objects.draft.messages.AIMessage;
import me.loopbreak.hermesanalyzer.objects.models.Model;
import me.loopbreak.hermesanalyzer.objects.platform.Platform;
import me.loopbreak.hermesanalyzer.objects.platform.PlatformProvider;
import me.loopbreak.hermesanalyzer.objects.request.CreateMessageRequest;
import me.loopbreak.hermesanalyzer.repository.ChatEntityRepository;
import me.loopbreak.hermesanalyzer.repository.PromptIterationEntityRepository;
import me.loopbreak.hermesanalyzer.repository.message.AiMessageRepository;
import me.loopbreak.hermesanalyzer.repository.message.UserMessageRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;

@Service
public class ChatService {


    private final ChatEntityRepository chatEntityRepository;
    private final PromptIterationEntityRepository promptIterationEntityRepository;
    private final AiMessageRepository aiMessageRepository;
    private final UserMessageRepository userMessageRepository;
    private final InstanceService instanceService;

    public ChatService(ChatEntityRepository chatEntityRepository,
                       PromptIterationEntityRepository promptIterationEntityRepository, AiMessageRepository aiMessageRepository, UserMessageRepository userMessageRepository, InstanceService instanceService) {
        this.chatEntityRepository = chatEntityRepository;
        this.promptIterationEntityRepository = promptIterationEntityRepository;
        this.aiMessageRepository = aiMessageRepository;
        this.userMessageRepository = userMessageRepository;
        this.instanceService = instanceService;
    }

    public ChatEntity getChat(Long chat) {
        ChatEntity chatEntity = chatEntityRepository.findById(chat).orElse(null);

        if (chatEntity == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat not found");

        return chatEntity;
    }

    public void setFinalized(ChatEntity chat, boolean finalized) {
        chat.setFinalized(finalized);
        chatEntityRepository.save(chat);
    }

    @Transactional
    public <T extends MessageEntity> @NotNull T createMessage(CreateMessageRequest request, ChatEntity chatEntity) {

        if (chatEntity.isFinalized())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Chat is finalized");


        PromptIterationEntity promptIteration = chatEntity.getLastIteration();

        long promptIterationCount = chatEntity.getPromptIterations().stream()
                .filter(p -> p.getType().equals(request.promptType()))
                .count();

        if (promptIteration == null || !promptIteration.getType().equals(request.promptType())) {
            if (promptIterationCount >= chatEntity.getIntentInstance().getMaxRepeatingPrompt() &&
                promptIteration != null &&
                promptIteration.getType().equals(request.promptType())) {
                setFinalized(chatEntity, true);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prompt iteration limit reached (" + promptIterationCount + "/" + chatEntity.getIntentInstance().getMaxRepeatingPrompt() + ")");
            }

            promptIteration = new PromptIterationEntity(request.promptType(), chatEntity, chatEntity.getPromptIterations().size());
            promptIteration = promptIterationEntityRepository.save(promptIteration);

            chatEntity.getPromptIterations().add(promptIteration);
        } else {
            boolean hasBeenScored = promptIteration.getMessages().stream()
                                            .filter(AIMessageEntity.class::isInstance)
                                            .filter(m -> ((AIMessageEntity) m).getScore() != -1)
                                            .count() > 0;
            if (hasBeenScored) {
                if (promptIterationCount >= chatEntity.getIntentInstance().getMaxRepeatingPrompt()) {
                    setFinalized(chatEntity, true);
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prompt iteration limit reached (" + promptIterationCount + "/" + chatEntity.getIntentInstance().getMaxRepeatingPrompt() + ")");
                }

                promptIteration = new PromptIterationEntity(request.promptType(), chatEntity, chatEntity.getPromptIterations().size());
                promptIteration = promptIterationEntityRepository.save(promptIteration);

                chatEntity.getPromptIterations().add(promptIteration);
            } else {
                MessageEntity lastMessage = promptIteration.getMessages().stream()
                        .sorted(Comparator.comparing(MessageEntity::getTimestamp).reversed())
                        .findFirst()
                        .orElse(null);

                if (lastMessage != null) {
                    if (request.getMessageType().equalsIgnoreCase("user") &&
                        lastMessage instanceof UserMessageEntity userMessage) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Last message was user message");
                    } else if (request.getMessageType().equalsIgnoreCase("ai") &&
                               lastMessage instanceof AIMessageEntity aiMessage) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Last message was AI message");
                    }
                }
            }
        }

        T messageEntity = (T) request.toMessageEntity(promptIteration);

        if (messageEntity instanceof AIMessageEntity aiMessage) {
            aiMessageRepository.save(aiMessage);
        } else if (messageEntity instanceof UserMessageEntity userMessage) {
            userMessageRepository.save(userMessage);
        }

        promptIteration.getMessages().add(messageEntity);

        if (request.getMessageType().equalsIgnoreCase("ai") &&
            request.score() == -1 &&
            promptIteration.getMessages().stream()
                    .filter(AIMessageEntity.class::isInstance)
                    .filter(m -> ((AIMessageEntity) m).getScore() == -1)
                    .count() > chatEntity.getIntentInstance().getMaxErrors()) {
            setFinalized(chatEntity, true);
        }

        if (promptIteration.getMessages().stream()
                    .filter(AIMessageEntity.class::isInstance)
                    .filter(m -> ((AIMessageEntity) m).getScore() != -1)
                    .count() > 1) {
            setFinalized(chatEntity, true);
        }

        return messageEntity;
    }

    /**
     * TODO: Add evaluation of the message, need the evaluator bridge in order to work
     *
     * @param chatEntity
     * @return
     */
    public AIMessageEntity generateMessage(ChatEntity chatEntity) {
        IntentInstanceEntity intentInstance = chatEntity.getIntentInstance();
        ModelSettingsEntity modelSettings = intentInstance.getModelSettings();
        Platform platform = PlatformProvider.getProvider(intentInstance.getPlatform());

        if (platform == null)
            throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Platform not found");

        Model model = platform.getModel(modelSettings);

        if (model == null)
            throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Model not found");

        PromptIterationEntity promptIteration = chatEntity.getLastIteration();

        if (promptIteration == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No prompt iteration found");

        MessageEntity lastMessage = promptIteration.getMessages()
                .stream()
                .max(Comparator.comparing(MessageEntity::getTimestamp))
                .orElse(null);

        if (lastMessage == null || lastMessage instanceof AIMessageEntity)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user message found");

        AIMessage message = model.send(chatEntity.toDraft()).join();

//        TODO: Add evaluation of the message

        AIMessageEntity aiMessage = createMessage(new CreateMessageRequest(promptIteration.getType(),
                message.getContent(), 0.0, false), chatEntity);

        if (aiMessage == null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create AI message");

        return aiMessage;
    }
}
