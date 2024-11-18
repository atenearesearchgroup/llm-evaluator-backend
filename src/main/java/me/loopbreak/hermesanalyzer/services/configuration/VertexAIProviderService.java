package me.loopbreak.hermesanalyzer.services.configuration;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vertexai.VertexAI;
import me.loopbreak.hermesanalyzer.objects.platform.connectors.vertex.VertexConnectionProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class VertexAIProviderService extends AbstractProviderService<VertexConnectionProperties, VertexAI> {

    private static VertexAIProviderService instance;

    @Autowired
    public VertexAIProviderService(VertexConnectionProperties options) {
        super(options);
        instance = this;
    }

    @Override
    public VertexAI getApi() {
        Resource resource = getOptions().getCredentialsUri();
        if (resource == null) {
            return new VertexAI(getOptions().getProjectId(), getOptions().getLocation());
        }
        Credentials credentials = null;

        try {
            credentials = GoogleCredentials.fromStream(resource.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new VertexAI(getOptions().getProjectId(), getOptions().getLocation(), credentials);
    }

    public static VertexAIProviderService getInstance() {
        if (instance == null) {
            System.out.println("VertexAIProviderService instance is null");
        }
        return instance;
    }
}
