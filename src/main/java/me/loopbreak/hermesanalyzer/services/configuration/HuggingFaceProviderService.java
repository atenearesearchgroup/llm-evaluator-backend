package me.loopbreak.hermesanalyzer.services.configuration;

import me.loopbreak.hermesanalyzer.objects.platform.connectors.huggingface.HuggingfaceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HuggingFaceProviderService extends AbstractProviderService<HuggingfaceProperties, Void> {

    private static HuggingFaceProviderService instance;

    @Autowired
    public HuggingFaceProviderService(HuggingfaceProperties options) {
        super(options);
        instance = this;
    }

    @Override
    public Void getApi() {
        return null;
    }

    public static HuggingFaceProviderService getInstance() {
        if (instance == null) {
            System.out.println("HuggingFaceProviderService instance is null");
        }
        return instance;
    }
}
