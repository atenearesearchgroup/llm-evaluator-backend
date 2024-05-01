package me.loopbreak.hermesanalyzer.configuration;

import me.loopbreak.hermesanalyzer.objects.platform.connectors.openai.OpenAiProperties;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenAIProviderService extends AbstractProviderService<OpenAiProperties, OpenAiApi> {

    @Autowired
    public OpenAIProviderService(OpenAiProperties options) {
        super(options);
    }

    @Override
    public OpenAiApi getApi() {
        return new OpenAiApi(getOptions().getApiKey());
    }

    public static OpenAIProviderService getInstance() {
        return BeanUtils.instantiateClass(OpenAIProviderService.class);
    }
}
