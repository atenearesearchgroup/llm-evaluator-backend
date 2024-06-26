package me.loopbreak.hermesanalyzer.objects.platform.providers;

import me.loopbreak.hermesanalyzer.services.configuration.ReplicateProviderService;
import me.loopbreak.hermesanalyzer.objects.models.Model;
import me.loopbreak.hermesanalyzer.objects.models.ModelImpl;
import me.loopbreak.hermesanalyzer.objects.models.ModelSettings;
import me.loopbreak.hermesanalyzer.objects.platform.Platform;
import me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.ReplicateApi;
import me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.ReplicateChatClient;
import me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.ReplicateOptions;
import org.springframework.ai.chat.ChatClient;

import java.util.List;

public class ReplicateProvider implements Platform {

    private ReplicateApi replicateApi;

    public ReplicateProvider() {
        this.replicateApi = ReplicateProviderService.getInstance().getApi();
    }

    public ReplicateProvider(ReplicateApi replicateApi) {
        this.replicateApi = replicateApi;
    }

    @Override
    public Model getModel(ModelSettings modelSettings) {
        ReplicateOptions replicateOptions = new ReplicateOptions();

        replicateOptions.setOfficial(modelSettings.getModelOwner() != null && modelSettings.getVersion() == null);

        if (replicateOptions.official()) {
            replicateOptions.setModelName(modelSettings.getModelName());
            replicateOptions.setModelOwner(modelSettings.getModelOwner());
        } else {
            replicateOptions.setVersion(modelSettings.getVersion());
        }

        ChatClient chatClient = new ReplicateChatClient(replicateApi, replicateOptions);

        return new ModelImpl(modelSettings, this, chatClient);
    }

    @Override
    public List<String> getAvailableModels() {
        return List.of();
    }

//    OllamaChatClient
}
