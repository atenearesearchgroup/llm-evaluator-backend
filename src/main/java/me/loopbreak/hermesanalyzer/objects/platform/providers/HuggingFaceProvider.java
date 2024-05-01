package me.loopbreak.hermesanalyzer.objects.platform.providers;

import me.loopbreak.hermesanalyzer.configuration.HuggingFaceProviderService;
import me.loopbreak.hermesanalyzer.objects.models.Model;
import me.loopbreak.hermesanalyzer.objects.models.ModelImpl;
import me.loopbreak.hermesanalyzer.objects.models.ModelSettings;
import me.loopbreak.hermesanalyzer.objects.platform.Platform;
import me.loopbreak.hermesanalyzer.objects.platform.connectors.huggingface.HuggingfaceProperties;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.huggingface.HuggingfaceChatClient;

import java.util.List;

public class HuggingFaceProvider implements Platform {

    private HuggingfaceProperties properties;

    public HuggingFaceProvider() {
        properties = HuggingFaceProviderService.getInstance().getOptions();
    }

    @Override
    public Model getModel(ModelSettings modelSettings) {
        ChatClient chatClient = new HuggingfaceChatClient(properties.getApiKey(), properties.getUrl());

        return new ModelImpl(modelSettings, this, chatClient);
    }

    @Override
    public List<String> getAvailableModels() {
        return List.of();
    }
}
