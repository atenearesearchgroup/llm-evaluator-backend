package me.loopbreak.hermesanalyzer.configuration;

import me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.ReplicateConnectionProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplicateProviderService extends AbstractProviderService<ReplicateConnectionProperties> {

    @Autowired
    public ReplicateProviderService(ReplicateConnectionProperties options) {
        super(options);
    }

    public static ReplicateProviderService getInstance() {
        return BeanUtils.instantiateClass(ReplicateProviderService.class);
    }
}
