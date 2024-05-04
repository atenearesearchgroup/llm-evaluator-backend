package me.loopbreak.hermesanalyzer.controllers.draft;

import me.loopbreak.hermesanalyzer.entity.DraftEntity;
import me.loopbreak.hermesanalyzer.entity.IntentInstanceEntity;
import me.loopbreak.hermesanalyzer.entity.messages.AIMessageEntity;
import me.loopbreak.hermesanalyzer.entity.messages.MessageEntity;
import me.loopbreak.hermesanalyzer.entity.messages.PromptIterationEntity;
import me.loopbreak.hermesanalyzer.entity.messages.UserMessageEntity;
import me.loopbreak.hermesanalyzer.objects.request.CreateMessageRequest;
import me.loopbreak.hermesanalyzer.repository.DraftEntityRepository;
import me.loopbreak.hermesanalyzer.repository.IntentInstanceRepository;
import me.loopbreak.hermesanalyzer.repository.PromptIterationEntityRepository;
import me.loopbreak.hermesanalyzer.repository.message.AiMessageRepository;
import me.loopbreak.hermesanalyzer.repository.message.UserMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping(value = "/instance", produces = MediaType.APPLICATION_JSON_VALUE)
public class InstanceController {

    private final IntentInstanceRepository intentInstanceRepository;
    private final DraftEntityRepository draftEntityRepository;
    private final PromptIterationEntityRepository promptIterationEntityRepository;
    private final AiMessageRepository aiMessageRepository;
    private final UserMessageRepository userMessageRepository;

    @Autowired
    public InstanceController(IntentInstanceRepository intentInstanceRepository,
                              DraftEntityRepository draftEntityRepository,
                              PromptIterationEntityRepository promptIterationEntityRepository, AiMessageRepository aiMessageRepository, UserMessageRepository userMessageRepository) {
        this.intentInstanceRepository = intentInstanceRepository;
        this.draftEntityRepository = draftEntityRepository;
        this.promptIterationEntityRepository = promptIterationEntityRepository;
        this.aiMessageRepository = aiMessageRepository;
        this.userMessageRepository = userMessageRepository;
    }

    @PostMapping("/{instance}/drafts")
    public DraftEntity createDrafts(@PathVariable Long instance) {
        IntentInstanceEntity instanceEntity = intentInstanceRepository.findById(instance).orElseThrow();

        int iterations = instanceEntity.getDrafts().size();
        if (instanceEntity.getMaxDrafts() <= iterations) {
            throw new IllegalArgumentException("Max drafts reached");
        }

        DraftEntity draft = new DraftEntity(instanceEntity, iterations);

        draftEntityRepository.save(draft);

        return draft;
    }

    @GetMapping("/{instance}/drafts")
    public List<DraftEntity> getDrafts(@PathVariable Long instance) {
        IntentInstanceEntity instanceEntity = intentInstanceRepository.findById(instance).orElseThrow();
        return instanceEntity.getDrafts();
    }

    @GetMapping("/{instance}/drafts/{draft}")
    public DraftEntity getDraft(@PathVariable Long instance, @PathVariable Integer draft) {
        return draftEntityRepository.findByIntentInstance_IdAndDraftNumber(instance, draft);
    }

    @PostMapping("/{instance}/drafts/{draft}/message")
    public <T extends MessageEntity> T createMessage(@PathVariable Long instance,
                                                     @PathVariable Integer draft,
                                                     @RequestBody CreateMessageRequest message) {
        DraftEntity draftEntity = draftEntityRepository.findByIntentInstance_IdAndDraftNumber(instance, draft);

        PromptIterationEntity promptIteration = draftEntity.getPromptIterations()
                .stream().max(Comparator.comparing(PromptIterationEntity::getIteration))
                .orElse(null);

        if (promptIteration == null || !promptIteration.getType().equals(message.promptType())) {
            promptIteration = new PromptIterationEntity(message.promptType(), draftEntity, 0);
            promptIteration = promptIterationEntityRepository.save(promptIteration);
        }

        T messageEntity = (T) message.toMessageEntity(promptIteration);

        if (messageEntity instanceof AIMessageEntity aiMessage) {
            aiMessageRepository.save(aiMessage);
        } else if (messageEntity instanceof UserMessageEntity userMessage) {
            userMessageRepository.save(userMessage);
        }

        return messageEntity;
    }

}
