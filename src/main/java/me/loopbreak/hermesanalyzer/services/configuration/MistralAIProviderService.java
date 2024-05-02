package me.loopbreak.hermesanalyzer.services.configuration;

import me.loopbreak.hermesanalyzer.objects.platform.connectors.mistral.MistralAiProperties;
import org.springframework.ai.mistralai.api.MistralAiApi;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MistralAIProviderService extends AbstractProviderService<MistralAiProperties, MistralAiApi> {

    @Autowired
    public MistralAIProviderService(MistralAiProperties options) {
        super(options);
    }

    @Override
    public MistralAiApi getApi() {
        return new MistralAiApi(getOptions().getApiKey());
    }

    public static MistralAIProviderService getInstance() {
        return BeanUtils.instantiateClass(MistralAIProviderService.class);
    }
}
