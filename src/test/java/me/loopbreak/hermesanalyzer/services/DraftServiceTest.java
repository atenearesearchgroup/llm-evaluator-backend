package me.loopbreak.hermesanalyzer.services;

import me.loopbreak.hermesanalyzer.entity.DraftEntity;
import me.loopbreak.hermesanalyzer.entity.EvaluationSettings;
import me.loopbreak.hermesanalyzer.entity.IntentInstanceEntity;
import me.loopbreak.hermesanalyzer.entity.IntentModelEntity;
import me.loopbreak.hermesanalyzer.entity.messages.MessageEntity;
import me.loopbreak.hermesanalyzer.entity.messages.PromptIterationEntity;
import me.loopbreak.hermesanalyzer.objects.models.ModelSettings;
import me.loopbreak.hermesanalyzer.objects.request.CreateInstanceRequest;
import me.loopbreak.hermesanalyzer.objects.request.CreateMessageRequest;
import me.loopbreak.hermesanalyzer.objects.request.CreateModelRequest;
import me.loopbreak.hermesanalyzer.repository.IntentInstanceRepository;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
class DraftServiceTest {

    private IntentService intentService;
    private InstanceService instanceService;
    private DraftService draftService;

    private IntentModelEntity model = null;
    private IntentInstanceEntity instance;
    @Autowired
    private IntentInstanceRepository intentInstanceRepository;

    @Autowired
    public DraftServiceTest(IntentService intentService, InstanceService instanceService, DraftService draftService) {
        this.intentService = intentService;
        this.instanceService = instanceService;
        this.draftService = draftService;
    }

    @BeforeEach
    public void setUp() {

        model = intentService.getModel("test");

        if (model == null)
            model = intentService.createModel(new CreateModelRequest("test", "Test"));

        instance = intentService.createInstance("test",
                new CreateInstanceRequest("ollama", "Test Instance",
                        ModelSettings.builder().modelName("mistral").build(),
                        new EvaluationSettings(1, 1, 1)));

    }

    @AfterEach
    public void tearDown() {
        intentInstanceRepository.delete(instance);
    }

    @Test
    void createMessage() {
        CreateMessageRequest request = new CreateMessageRequest("test", "", 0, true);
        DraftEntity draft = instanceService.createDraft(instance);

        MessageEntity result = draftService.createMessage(request, draft);

        assertThat(result).isNotNull();
        assertThat(draft.getPromptIterations()).hasSize(1);

        PromptIterationEntity actual = draft.getPromptIterations().get(0);
        assertThat(actual.getType()).isEqualTo(request.promptType());
        assertThat(actual.getIteration()).isEqualTo(0);
        assertThat(actual.getMessages()).hasSize(1);
    }

    @Test
    void createMessage_exceededPromptMessages() {
        CreateMessageRequest request = new CreateMessageRequest("test", "", null, true);
        CreateMessageRequest aiRequest = new CreateMessageRequest("test", "", 1, true);
        DraftEntity draft = instanceService.createDraft(instance);

        draftService.createMessage(request, draft);
        draftService.createMessage(aiRequest, draft);

        assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> draftService.createMessage(request, draft))
                .has(new Condition<>(e -> e.getReason().equals("Messages per prompt limit reached (1/1)"), "Max messages per prompt type reached"));
    }

    @Test
    void createMessage_exceededPromptIteration() {
        CreateMessageRequest request = new CreateMessageRequest("test", "", null, true);
        CreateMessageRequest requestType2 = new CreateMessageRequest("test2", "", null, true);
        DraftEntity draft = instanceService.createDraft(instance);

        draftService.createMessage(request, draft);
        draftService.createMessage(requestType2, draft);

        assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> draftService.createMessage(request, draft))
                .has(new Condition<>(e -> e.getReason().equals("Prompt iteration limit reached (1/1)"), "Max prompt iteration reached"));
    }

    @Test
    void createMessage_draftIsFinalized() {
        CreateMessageRequest request = new CreateMessageRequest("test", "", null, true);
        DraftEntity draft = instanceService.createDraft(instance);

        draftService.setFinalized(draft, true);

        assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> draftService.createMessage(request, draft))
                .has(new Condition<>(e -> e.getReason().equals("Draft is finalized"), "Draft is already finalized"));
    }

}