package me.loopbreak.hermesanalyzer.services.configuration;

import me.loopbreak.hermesanalyzer.objects.platform.connectors.mistral.MistralAiProperties;
import org.springframework.ai.mistralai.api.MistralAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MistralAIProviderService extends AbstractProviderService<MistralAiProperties, MistralAiApi> {

    private static MistralAIProviderService instance;

    @Autowired
    public MistralAIProviderService(MistralAiProperties options) {
        super(options);
        instance = this;
    }

    @Override
    public MistralAiApi getApi() {
        return new MistralAiApi(getOptions().getApiKey());
    }

    public static MistralAIProviderService getInstance() {
        if (instance == null) {
            System.out.println("MistralAIProviderService instance is null");
        }
        return instance;
//        return BeanUtils.instantiateClass(MistralAIProviderService.class);
    }
}
