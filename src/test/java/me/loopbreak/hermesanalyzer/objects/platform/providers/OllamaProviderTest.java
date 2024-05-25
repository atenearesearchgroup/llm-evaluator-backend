package me.loopbreak.hermesanalyzer.objects.platform.providers;

import me.loopbreak.hermesanalyzer.objects.draft.Draft;
import me.loopbreak.hermesanalyzer.objects.draft.PromptIteration;
import me.loopbreak.hermesanalyzer.objects.draft.PromptPhase;
import me.loopbreak.hermesanalyzer.objects.draft.messages.AIMessage;
import me.loopbreak.hermesanalyzer.objects.draft.messages.Message;
import me.loopbreak.hermesanalyzer.objects.models.Model;
import me.loopbreak.hermesanalyzer.objects.models.ModelSettings;
import me.loopbreak.hermesanalyzer.objects.platform.DefaultPlatforms;
import me.loopbreak.hermesanalyzer.objects.platform.Platform;
import me.loopbreak.hermesanalyzer.objects.platform.PlatformProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest
@Disabled
class OllamaProviderTest {

    private Platform ollamaProvider;
    private Model model;

    @BeforeEach
    void setUp() {
        ollamaProvider = PlatformProvider.getProvider(DefaultPlatforms.OLLAMA);
        model = ollamaProvider.getModel(ModelSettings.builder()
                .modelName("mistral:latest")
                .systemPrompt("You are a helpful modeling assistant. You use PlantUML notation. You have a deep knowledge about class diagrams and deep understanding of system requirements. Stick to what is defined in the instructions."));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAvailableModels() {
        assertThatCode(ollamaProvider::getAvailableModels).doesNotThrowAnyException();
    }

    @Test
    @Disabled
    void sendRequest() {
        Draft draft = new Draft(0);
        PromptPhase promptPhase = new PromptPhase("first_run");
        PromptIteration promptIteration = new PromptIteration(0);
        draft.getHistory().add(promptPhase);
        promptPhase.getIterations().add(promptIteration);
        promptIteration.addMessage(Message.userMessage()
                .content("I would like to create a PlantUML diagram (as a diagram class) for a Courses System. It is known that Courses have a name and a number of credits. Each Courses is imparted by one or more Professors, who have a name. Professors could participate in any number of Courses. For a Course to exist, it must aggregate, at least, five Students, where each Student hast a name. Students can be enrolled in any number of courses. Finally, Students can be acommodated in Dormitories, where each dormitory can have from one to four Students. Besides, each Dormitory has a price. Please, draw an UML diagram using PlantUML (as a diagram class). Do not include any operation.")
                .build());

        CompletableFuture<AIMessage> response = model.send(draft);

        AIMessage aimessage = response.join();

        System.out.println(aimessage);
        assertThat(aimessage.getContent()).isNotEmpty();

    }
}