package me.loopbreak.hermesanalyzer.objects.platform.connectors.ollama;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.ai.ollama.connection")
public class OllamaConnectionProperties {
    public static final String CONFIG_PREFIX = "spring.ai.ollama.connection";
    private String baseurl;

    public OllamaConnectionProperties() {
    }

    public String getBaseUrl() {
        return this.baseurl;
    }

    public void setBaseUrl(String baseurl) {
        this.baseurl = baseurl;
    }

}
