package me.loopbreak.hermesanalyzer.objects.platform.providers;

import me.loopbreak.hermesanalyzer.objects.models.Model;
import me.loopbreak.hermesanalyzer.objects.models.ModelImpl;
import me.loopbreak.hermesanalyzer.objects.models.ModelSettings;
import me.loopbreak.hermesanalyzer.objects.platform.Platform;
import me.loopbreak.hermesanalyzer.objects.platform.connectors.ollama.OllamaExtraConnector;
import me.loopbreak.hermesanalyzer.services.configuration.OllamaProviderService;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OllamaProvider implements Platform {

    private OllamaApi ollamaApi;
    private OllamaExtraConnector ollamaExtraConnector = OllamaExtraConnector.getInstance();

    @Autowired
    public OllamaProvider(OllamaProviderService ollamaProviderService) {
        this.ollamaApi = ollamaProviderService.getApi();
    }

    @Override
    public Model getModel(ModelSettings modelSettings) {
        OllamaOptions ollamaOptions = OllamaOptions.create()
                .withModel(modelSettings.getModelName());

        if (modelSettings.getTemperature() >= 0)
            ollamaOptions.withTemperature(modelSettings.getTemperature());

        if (modelSettings.getTopP() >= 0)
            ollamaOptions.withTopP(modelSettings.getTopP());

        if (modelSettings.getFrequencyPenalty() >= 0)
            ollamaOptions.withFrequencyPenalty(modelSettings.getFrequencyPenalty());

        if (modelSettings.getPresencePenalty() >= 0)
            ollamaOptions.withPresencePenalty(modelSettings.getPresencePenalty());

        ChatClient chatClient = new OllamaChatClient(ollamaApi)
                .withDefaultOptions(ollamaOptions);

        return new ModelImpl(modelSettings, this, chatClient);
    }

    @Override
    public List<String> getAvailableModels() {
        return ollamaExtraConnector.getAvailableModels();
    }

}
