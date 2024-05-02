package me.loopbreak.hermesanalyzer.services.configuration;

import me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.ReplicateApi;
import me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.ReplicateConnectionProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplicateProviderService extends AbstractProviderService<ReplicateConnectionProperties, ReplicateApi> {

    @Autowired
    public ReplicateProviderService(ReplicateConnectionProperties options) {
        super(options);
    }

    @Override
    public ReplicateApi getApi() {
        return new ReplicateApi(getOptions().getToken());
    }

    public static ReplicateProviderService getInstance() {
        return BeanUtils.instantiateClass(ReplicateProviderService.class);
    }
}
