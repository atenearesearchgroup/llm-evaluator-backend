package me.loopbreak.hermesanalyzer.configuration;

import me.loopbreak.hermesanalyzer.objects.platform.connectors.ollama.OllamaConnectionProperties;
import me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.ReplicateApi;
import me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.ReplicateConnectionProperties;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OllamaProviderService extends AbstractProviderService<OllamaConnectionProperties, OllamaApi> {

    @Autowired
    public OllamaProviderService(OllamaConnectionProperties options) {
        super(options);
    }

    @Override
    public OllamaApi getApi() {
        return new OllamaApi(getOptions().getBaseUrl());
    }

    public static OllamaProviderService getInstance() {
        return BeanUtils.instantiateClass(OllamaProviderService.class);
    }
}
