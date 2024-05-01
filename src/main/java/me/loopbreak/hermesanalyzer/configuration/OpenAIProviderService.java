package me.loopbreak.hermesanalyzer.configuration;

import me.loopbreak.hermesanalyzer.objects.platform.connectors.openai.OpenAiProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenAIProviderService extends AbstractProviderService<OpenAiProperties> {

    @Autowired
    public OpenAIProviderService(OpenAiProperties options) {
        super(options);
    }

    public static OpenAIProviderService getInstance() {
        return BeanUtils.instantiateClass(OpenAIProviderService.class);
    }
}
