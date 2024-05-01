package me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.ai.replicate.connection")
public class ReplicateConnectionProperties {
    public static final String CONFIG_PREFIX = "spring.ai.replicate.connection";
    private String token;

    public ReplicateConnectionProperties() {
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
