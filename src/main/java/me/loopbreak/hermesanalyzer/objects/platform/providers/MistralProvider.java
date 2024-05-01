package me.loopbreak.hermesanalyzer.objects.platform.providers;

import me.loopbreak.hermesanalyzer.configuration.MistralAIProviderService;
import me.loopbreak.hermesanalyzer.objects.models.Model;
import me.loopbreak.hermesanalyzer.objects.models.ModelImpl;
import me.loopbreak.hermesanalyzer.objects.models.ModelSettings;
import me.loopbreak.hermesanalyzer.objects.platform.Platform;
import me.loopbreak.hermesanalyzer.objects.platform.connectors.mistral.MistralAiProperties;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.mistralai.MistralAiChatClient;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.ai.mistralai.api.MistralAiApi;

import java.util.Arrays;
import java.util.List;

public class MistralProvider implements Platform {

    private MistralAiApi mistralAiApi;

    public MistralProvider() {
        this.mistralAiApi = MistralAIProviderService.getInstance().getApi();
    }

    @Override
    public Model getModel(ModelSettings modelSettings) {
        MistralAiChatOptions.Builder openAiOptions = MistralAiChatOptions.builder()
                .withModel(modelSettings.getModelName());

        if (modelSettings.getTemperature() >= 0)
            openAiOptions.withTemperature(modelSettings.getTemperature());

        if (modelSettings.getTopP() >= 0)
            openAiOptions.withTopP(modelSettings.getTopP());

        ChatClient chatClient = new MistralAiChatClient(mistralAiApi, openAiOptions.build());

        return new ModelImpl(modelSettings, this, chatClient);
    }

    @Override
    public List<String> getAvailableModels() {
        return Arrays.stream(MistralAiApi.ChatModel.values()).map(MistralAiApi.ChatModel::getValue).toList();
    }
}
