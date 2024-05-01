package me.loopbreak.hermesanalyzer.configuration;

import me.loopbreak.hermesanalyzer.objects.platform.connectors.vertex.VertexConnectionProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VertexAIProviderService extends AbstractProviderService<VertexConnectionProperties> {

    @Autowired
    public VertexAIProviderService(VertexConnectionProperties options) {
        super(options);
    }

    public static VertexAIProviderService getInstance() {
        return BeanUtils.instantiateClass(VertexAIProviderService.class);
    }
}
