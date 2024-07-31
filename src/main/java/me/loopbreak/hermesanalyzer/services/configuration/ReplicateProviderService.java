package me.loopbreak.hermesanalyzer.services.configuration;

import me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.ReplicateApi;
import me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.ReplicateConnectionProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplicateProviderService extends AbstractProviderService<ReplicateConnectionProperties, ReplicateApi> {

    private static ReplicateProviderService instance;

    @Autowired
    public ReplicateProviderService(ReplicateConnectionProperties options) {
        super(options);
        instance = this;
    }

    @Override
    public ReplicateApi getApi() {
        return new ReplicateApi(getOptions().getToken());
    }

    public static ReplicateProviderService getInstance() {
        if (instance == null) {
            System.out.println("ReplicateProviderService instance is null");
        }
        return instance;
    }
}
