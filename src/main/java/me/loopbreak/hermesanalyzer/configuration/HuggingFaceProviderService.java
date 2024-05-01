package me.loopbreak.hermesanalyzer.configuration;

import me.loopbreak.hermesanalyzer.objects.platform.connectors.huggingface.HuggingfaceProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HuggingFaceProviderService extends AbstractProviderService<HuggingfaceProperties> {

    @Autowired
    public HuggingFaceProviderService(HuggingfaceProperties options) {
        super(options);
    }

    public static HuggingFaceProviderService getInstance() {
        return BeanUtils.instantiateClass(HuggingFaceProviderService.class);
    }
}
