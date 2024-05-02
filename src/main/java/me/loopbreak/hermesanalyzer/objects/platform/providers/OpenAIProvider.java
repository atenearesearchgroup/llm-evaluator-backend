package me.loopbreak.hermesanalyzer.objects.platform.providers;

import me.loopbreak.hermesanalyzer.services.configuration.OpenAIProviderService;
import me.loopbreak.hermesanalyzer.objects.models.Model;
import me.loopbreak.hermesanalyzer.objects.models.ModelImpl;
import me.loopbreak.hermesanalyzer.objects.models.ModelSettings;
import me.loopbreak.hermesanalyzer.objects.platform.Platform;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;

import java.util.Arrays;
import java.util.List;

public class OpenAIProvider implements Platform {

    private OpenAiApi openAiApi;

    public OpenAIProvider() {
        this.openAiApi = OpenAIProviderService.getInstance().getApi();
    }

    @Override
    public Model getModel(ModelSettings modelSettings) {
        OpenAiChatOptions.Builder openAiOptions = OpenAiChatOptions.builder()
                .withModel(modelSettings.getModelName());

        if (modelSettings.getTemperature() >= 0)
            openAiOptions.withTemperature(modelSettings.getTemperature());

        if (modelSettings.getTopP() >= 0)
            openAiOptions.withTopP(modelSettings.getTopP());

        if (modelSettings.getFrequencyPenalty() >= 0)
            openAiOptions.withFrequencyPenalty(modelSettings.getFrequencyPenalty());

        if (modelSettings.getPresencePenalty() >= 0)
            openAiOptions.withPresencePenalty(modelSettings.getPresencePenalty());

        ChatClient chatClient = new OpenAiChatClient(openAiApi, openAiOptions.build());

        return new ModelImpl(modelSettings, this, chatClient);
    }

    @Override
    public List<String> getAvailableModels() {
        return Arrays.stream(OpenAiApi.ChatModel.values()).map(OpenAiApi.ChatModel::getValue).toList();
    }
}
