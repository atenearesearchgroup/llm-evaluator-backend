package me.loopbreak.hermesanalyzer.controllers;

import me.loopbreak.hermesanalyzer.entity.DraftEntity;
import me.loopbreak.hermesanalyzer.entity.messages.AIMessageEntity;
import me.loopbreak.hermesanalyzer.entity.messages.MessageEntity;
import me.loopbreak.hermesanalyzer.objects.request.CreateMessageRequest;
import me.loopbreak.hermesanalyzer.objects.request.UpdateDraftRequest;
import me.loopbreak.hermesanalyzer.repository.DraftEntityRepository;
import me.loopbreak.hermesanalyzer.repository.PromptIterationEntityRepository;
import me.loopbreak.hermesanalyzer.repository.message.AiMessageRepository;
import me.loopbreak.hermesanalyzer.repository.message.UserMessageRepository;
import me.loopbreak.hermesanalyzer.services.DraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4321"})
@RequestMapping("/draft")
public class DraftController {

    private final DraftEntityRepository draftEntityRepository;
    private final PromptIterationEntityRepository promptIterationEntityRepository;
    private final AiMessageRepository aiMessageRepository;
    private final UserMessageRepository userMessageRepository;
    private final DraftService draftService;

    @Autowired
    public DraftController(DraftEntityRepository draftEntityRepository,
                           PromptIterationEntityRepository promptIterationEntityRepository, AiMessageRepository aiMessageRepository, UserMessageRepository userMessageRepository, DraftService draftService) {
        this.draftEntityRepository = draftEntityRepository;
        this.promptIterationEntityRepository = promptIterationEntityRepository;
        this.aiMessageRepository = aiMessageRepository;
        this.userMessageRepository = userMessageRepository;
        this.draftService = draftService;
    }

    @GetMapping("/{draft}")
    public DraftEntity getDraft(@PathVariable Long draft) {
        return draftService.getDraft(draft);
    }

    @PutMapping("/{draft}")
    public DraftEntity updateDraft(@PathVariable Long draft, @RequestBody UpdateDraftRequest request) {
        DraftEntity draftEntity = draftService.getDraft(draft);

        request.update(draftEntity);

        return draftEntityRepository.save(draftEntity);
    }

    @PostMapping("/{draft}/message")
    public <T extends MessageEntity> T createMessage(@PathVariable Long draft,
                                                     @RequestBody CreateMessageRequest message) {
        DraftEntity draftEntity = draftService.getDraft(draft);

        return draftService.createMessage(message, draftEntity);
    }

    @PostMapping("/{draft}/message/generate")
    public AIMessageEntity generateMessage(@PathVariable Long draft) {
        DraftEntity draftEntity = draftService.getDraft(draft);

        return draftService.generateMessage(draftEntity);
    }

    @PostMapping("/{draft}/finish")
    public void finishDraft(@PathVariable Long draft, @RequestBody(required = false) Boolean finalize) {
        DraftEntity draftEntity = draftService.getDraft(draft);

        draftService.setFinalized(draftEntity, Objects.requireNonNullElse(finalize, true));
    }

}
