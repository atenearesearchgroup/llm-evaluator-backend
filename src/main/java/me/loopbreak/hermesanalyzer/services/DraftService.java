package me.loopbreak.hermesanalyzer.services;

import me.loopbreak.hermesanalyzer.entity.DraftEntity;
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
import me.loopbreak.hermesanalyzer.repository.DraftEntityRepository;
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
public class DraftService {


    private final DraftEntityRepository draftEntityRepository;
    private final PromptIterationEntityRepository promptIterationEntityRepository;
    private final AiMessageRepository aiMessageRepository;
    private final UserMessageRepository userMessageRepository;

    public DraftService(DraftEntityRepository draftEntityRepository,
                        PromptIterationEntityRepository promptIterationEntityRepository, AiMessageRepository aiMessageRepository, UserMessageRepository userMessageRepository) {
        this.draftEntityRepository = draftEntityRepository;
        this.promptIterationEntityRepository = promptIterationEntityRepository;
        this.aiMessageRepository = aiMessageRepository;
        this.userMessageRepository = userMessageRepository;
    }

    public DraftEntity getDraft(Long draft) {
        DraftEntity draftEntity = draftEntityRepository.findById(draft).orElse(null);

        if (draftEntity == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Draft not found");

        return draftEntity;
    }

    public void setFinalized(DraftEntity draft, boolean finalized) {
        draft.setFinalized(finalized);
        draftEntityRepository.save(draft);
    }

    @Transactional
    public <T extends MessageEntity> @NotNull T createMessage(CreateMessageRequest request, DraftEntity draftEntity) {

        if (draftEntity.isFinalized())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Draft is finalized");

        PromptIterationEntity promptIteration = draftEntity.getLastIteration();

        if (promptIteration == null || !promptIteration.getType().equals(request.promptType())) {
            long promptCount = draftEntity.getPromptIterations().stream().
                    filter(p -> p.getType().equals(request.promptType()))
                    .count();
            if (promptCount >= draftEntity.getIntentInstance().getMaxRepeatingPrompt())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Prompt iteration limit reached (" + promptCount + "/"
                        + draftEntity.getIntentInstance().getMaxRepeatingPrompt() + ")");

            promptIteration = new PromptIterationEntity(request.promptType(), draftEntity, draftEntity.getPromptIterations().size());
            promptIteration = promptIterationEntityRepository.save(promptIteration);

            draftEntity.getPromptIterations().add(promptIteration);
        } else {
            long messagesCount = promptIteration.getMessages().stream()
                    .filter(UserMessageEntity.class::isInstance)
                    .count();

//            take the last message
            MessageEntity lastMessage = promptIteration.getMessages().stream()
                    .sorted(Comparator.comparing(MessageEntity::getTimestamp).reversed())
                    .findFirst()
                    .orElse(null);

            System.out.println(lastMessage);

            if (lastMessage != null) {
                if (request.getMessageType().equalsIgnoreCase("user") &&
                    lastMessage instanceof UserMessageEntity userMessage) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Last message was user message");
                } else if (request.getMessageType().equalsIgnoreCase("ai") &&
                           lastMessage instanceof AIMessageEntity aiMessage) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Last message was AI message");
                }
            }

            if (!request.getMessageType().equalsIgnoreCase("ai") &&
                messagesCount >= draftEntity.getIntentInstance().getMaxK())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Messages per prompt limit reached (" + messagesCount + "/"
                        + draftEntity.getIntentInstance().getMaxK() + ")");

        }

        T messageEntity = (T) request.toMessageEntity(promptIteration);

        if (messageEntity instanceof AIMessageEntity aiMessage) {
            aiMessageRepository.save(aiMessage);
        } else if (messageEntity instanceof UserMessageEntity userMessage) {
            userMessageRepository.save(userMessage);
        }

        promptIteration.getMessages().add(messageEntity);

        return messageEntity;
    }

    public AIMessageEntity generateMessage(DraftEntity draftEntity) {
        IntentInstanceEntity intentInstance = draftEntity.getIntentInstance();
        ModelSettingsEntity modelSettings = intentInstance.getModelSettings();
        Platform platform = PlatformProvider.getProvider(intentInstance.getPlatform());

        if (platform == null)
            throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Platform not found");

        Model model = platform.getModel(modelSettings);

        if (model == null)
            throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Model not found");

        PromptIterationEntity promptIteration = draftEntity.getLastIteration();

        if (promptIteration == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No prompt iteration found");

        MessageEntity lastMessage = promptIteration.getMessages()
                .stream()
                .max(Comparator.comparing(MessageEntity::getTimestamp))
                .orElse(null);

        if (lastMessage == null || lastMessage instanceof AIMessageEntity)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user message found");

        AIMessage message = model.send(draftEntity.toDraft()).join();

//        TODO: Add evaluation of the message

        AIMessageEntity aiMessage = createMessage(new CreateMessageRequest(promptIteration.getType(),
                message.getContent(), 0, false), draftEntity);

        if (aiMessage == null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create AI message");

        return aiMessage;
    }
}
