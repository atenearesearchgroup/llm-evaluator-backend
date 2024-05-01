package me.loopbreak.hermesanalyzer.objects.draft;

import me.loopbreak.hermesanalyzer.objects.draft.messages.AIMessage;
import me.loopbreak.hermesanalyzer.objects.draft.messages.UserMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.StringJoiner;

class DraftTest {
    private Draft draft;

    @BeforeEach
    void setUp() {
        long currentMillis = System.currentTimeMillis();
        draft = new Draft(0);

        PromptPhase promptPhaseZero = new PromptPhase("0");
        PromptPhase promptPhaseOne = new PromptPhase("1");
        draft.getHistory().add(promptPhaseZero);
        draft.getHistory().add(promptPhaseOne);

        PromptIteration promptIterationZero = new PromptIteration(0, 0, new ArrayList<>());

        promptPhaseZero.getIterations().add(promptIterationZero);

        promptIterationZero.addValues(new UserMessage("0 PROMPT", new Timestamp(currentMillis)),
                new AIMessage("0 RESPONSE", 100,new Timestamp(currentMillis)));

        PromptIteration promptIterationOne = new PromptIteration(1, 1, new ArrayList<>());

        promptPhaseOne.getIterations().add(promptIterationOne);

        promptIterationOne.addValues(new UserMessage("1 PROMPT", new Timestamp(currentMillis + 100)),
                new AIMessage("1 RESPONSE", 100,new Timestamp(currentMillis + 100)));

        promptIterationZero.addValues(new UserMessage("0 PROMPT 2", new Timestamp(currentMillis + 200)),
                new AIMessage("0 RESPONSE 2", 100,new Timestamp(currentMillis + 200)));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getMessages() {

        StringJoiner sj = new StringJoiner("\n");

        draft.getMessages().forEach(message -> sj.add(message.toString()));

        System.out.println(sj);
    }
}