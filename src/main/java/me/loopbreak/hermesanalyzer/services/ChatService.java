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


    public static final int MESSAGE_INVALID_SYNTAX_SCORE = -1;
    public static final int MESSAGE_NOT_SET_SCORE = -2;
    private final ChatEntityRepository chatEntityRepository;
    private final PromptIterationEntityRepository promptIterationEntityRepository;
    private final AiMessageRepository aiMessageRepository;
    private final UserMessageRepository userMessageRepository;
    private final InstanceService instanceService;
    private final PlatformProvider platformProvider;

    public ChatService(ChatEntityRepository chatEntityRepository,
                       PromptIterationEntityRepository promptIterationEntityRepository,
                       AiMessageRepository aiMessageRepository,
                       UserMessageRepository userMessageRepository,
                       InstanceService instanceService,
                       PlatformProvider platformProvider) {
        this.chatEntityRepository = chatEntityRepository;
        this.promptIterationEntityRepository = promptIterationEntityRepository;
        this.aiMessageRepository = aiMessageRepository;
        this.userMessageRepository = userMessageRepository;
        this.instanceService = instanceService;
        this.platformProvider = platformProvider;
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

//       If there is no prompt iteration for the current state or the prompt type is different
        if (promptIteration == null || !promptIteration.getType().equals(request.promptType())) {
//            If the prompt iteration count is greater than the max repeating prompt
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
                    .anyMatch(m -> ((AIMessageEntity) m).getScore() >= 0);
//            If any message in the prompt iteration has been scored
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

//                Check if the last message is of the same type as the current message
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

        // If you try to send an invalid syntax message more than the max errors
        if (request.getMessageType().equalsIgnoreCase("ai") &&
            request.score() == MESSAGE_INVALID_SYNTAX_SCORE &&
            promptIteration.getMessages().stream()
                    .filter(AIMessageEntity.class::isInstance)
                    .filter(m -> ((AIMessageEntity) m).getScore() == MESSAGE_INVALID_SYNTAX_SCORE)
                    .count() > chatEntity.getIntentInstance().getMaxErrors()) {
            setFinalized(chatEntity, true);
        }

        if (promptIteration.getMessages().stream()
                    .filter(AIMessageEntity.class::isInstance)
                    .filter(m -> ((AIMessageEntity) m).getScore() != MESSAGE_NOT_SET_SCORE)
                    .count() > 1) {
            setFinalized(chatEntity, true);
        }

        return messageEntity;
    }

    public String generateMessage(ChatEntity chatEntity) {
        IntentInstanceEntity intentInstance = chatEntity.getIntentInstance();
        ModelSettingsEntity modelSettings = intentInstance.getModelSettings();
        Platform platform = platformProvider.getProvider(intentInstance.getPlatform());

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

        try {
            AIMessage message = model.send(chatEntity.toDraft()).join();
            return message.getContent();
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to generate message", exception);
        }
    }
}
