package me.loopbreak.hermesanalyzer.objects.platform.connectors.vertex;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vertexai.VertexAI;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

import java.io.IOException;

@ConfigurationProperties("spring.ai.vertex.ai.gemini")
public class VertexConnectionProperties {

    public static final String CONFIG_PREFIX = "spring.ai.vertex.ai.gemini";
    private String projectId;
    private String location;
    private Resource credentialsUri;

    public VertexConnectionProperties() {
    }

    public String getProjectId() {
        return this.projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Resource getCredentialsUri() {
        return this.credentialsUri;
    }

    public void setCredentialsUri(Resource credentialsUri) {
        this.credentialsUri = credentialsUri;
    }

    public VertexAI generate() throws IOException {
        if (getCredentialsUri() != null) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(getCredentialsUri().getInputStream());
            return new VertexAI(getProjectId(), getLocation(), credentials);
        } else {
            return new VertexAI(getProjectId(), getLocation());
        }
    }

}
