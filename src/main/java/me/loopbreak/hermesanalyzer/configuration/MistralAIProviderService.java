package me.loopbreak.hermesanalyzer.configuration;

import me.loopbreak.hermesanalyzer.objects.platform.connectors.mistral.MistralAiProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MistralAIProviderService extends AbstractProviderService<MistralAiProperties> {

    @Autowired
    public MistralAIProviderService(MistralAiProperties options) {
        super(options);
    }

    public static MistralAIProviderService getInstance() {
        return BeanUtils.instantiateClass(MistralAIProviderService.class);
    }
}
