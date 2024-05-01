package me.loopbreak.hermesanalyzer.objects.models;

import me.loopbreak.hermesanalyzer.exceptions.ModelSettingsException;
import me.loopbreak.hermesanalyzer.objects.draft.Draft;
import me.loopbreak.hermesanalyzer.objects.draft.messages.AIMessage;
import me.loopbreak.hermesanalyzer.objects.draft.messages.Message;
import me.loopbreak.hermesanalyzer.objects.platform.Platform;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ModelImpl implements Model {

    private ModelSettings modelSettings;
    private Platform platform;
    private ChatClient chatClient;
//    private ModelAPI ...

    public ModelImpl(ModelSettings modelSettings, Platform platform, ChatClient chatClient) {
        this.modelSettings = modelSettings;
        this.platform = platform;
        this.chatClient = chatClient;
    }

    public ModelSettings getModelSettings() {
        return modelSettings;
    }

    @Override
    public CompletableFuture<AIMessage> send(Draft draft) {
        if (!Objects.equals(draft.getSystemPrompt(), getModelSettings().getSystemPrompt())) {
            if (draft.getSystemPrompt() != null)
                throw new ModelSettingsException(getModelSettings(),
                        String.format("Draft system prompt (%s) does not match model system prompt",
                                draft.getSystemPrompt()));

            draft.setSystemPrompt(getModelSettings().getSystemPrompt());
        }

        List<Message> messages = draft.getMessages();

        SystemMessage systemMessage = new SystemMessage(getModelSettings().getSystemPrompt());

        List<org.springframework.ai.chat.messages.Message> springMessages = new ArrayList<>();

        springMessages.add(systemMessage);

        messages.stream().map(Message::toSpringMessage).forEach(springMessages::add);

        Prompt prompt = new Prompt(springMessages);

        return CompletableFuture.supplyAsync(() -> {
            ChatResponse response = chatClient.call(prompt);

            Generation generation = response.getResult();

            if (generation == null) {
                return null;
            }

            return new AIMessage(generation.getOutput().getContent(), -1, Timestamp.from(Instant.now()));
        });
    }

    public Platform getPlatform() {
        return platform;
    }
}
