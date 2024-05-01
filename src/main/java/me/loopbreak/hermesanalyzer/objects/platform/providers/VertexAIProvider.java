package me.loopbreak.hermesanalyzer.objects.platform.providers;

import com.google.cloud.vertexai.VertexAI;
import me.loopbreak.hermesanalyzer.configuration.VertexAIProviderService;
import me.loopbreak.hermesanalyzer.objects.models.Model;
import me.loopbreak.hermesanalyzer.objects.models.ModelImpl;
import me.loopbreak.hermesanalyzer.objects.models.ModelSettings;
import me.loopbreak.hermesanalyzer.objects.platform.Platform;
import me.loopbreak.hermesanalyzer.objects.platform.connectors.vertex.VertexConnectionProperties;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatClient;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatOptions;
import org.springframework.core.io.Resource;

import java.util.Arrays;
import java.util.List;

public class VertexAIProvider implements Platform {

    private VertexAI vertexAI;

    public VertexAIProvider() {
        VertexConnectionProperties properties = VertexAIProviderService.getInstance().getOptions();
        Resource resource = properties.getCredentialsUri();
//        Credentials credentials = GoogleCredentials.fromStream(resource.getInputStream());
        this.vertexAI = new VertexAI(properties.getProjectId(), properties.getLocation());
    }

    @Override
    public Model getModel(ModelSettings modelSettings) {
        VertexAiGeminiChatOptions.Builder geminiChatOptions = VertexAiGeminiChatOptions.builder()
                .withModel(modelSettings.getModelName());

        if (modelSettings.getTemperature() >= 0)
            geminiChatOptions.withTemperature(modelSettings.getTemperature());

        if (modelSettings.getTopP() >= 0)
            geminiChatOptions.withTopP(modelSettings.getTopP());

        ChatClient chatClient = new VertexAiGeminiChatClient(vertexAI, geminiChatOptions.build());

        return new ModelImpl(modelSettings, this, chatClient);
    }

    @Override
    public List<String> getAvailableModels() {
        return Arrays.stream(OpenAiApi.ChatModel.values()).map(OpenAiApi.ChatModel::getValue).toList();
    }
}
