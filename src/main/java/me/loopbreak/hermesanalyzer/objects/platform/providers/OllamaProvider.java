package me.loopbreak.hermesanalyzer.objects.platform.providers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import me.loopbreak.hermesanalyzer.exceptions.ModelConnectionException;
import me.loopbreak.hermesanalyzer.objects.models.Model;
import me.loopbreak.hermesanalyzer.objects.models.ModelImpl;
import me.loopbreak.hermesanalyzer.objects.models.ModelSettings;
import me.loopbreak.hermesanalyzer.objects.platform.Platform;
import me.loopbreak.hermesanalyzer.objects.platform.connectors.ollama.OllamaExtraConnector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class OllamaProvider implements Platform {

    private OllamaApi ollamaApi = new OllamaApi();
    private OllamaExtraConnector ollamaExtraConnector = OllamaExtraConnector.getInstance();

    public OllamaProvider() {
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
