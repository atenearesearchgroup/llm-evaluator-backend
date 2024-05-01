package me.loopbreak.hermesanalyzer.objects.platform.connectors.mistral;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.ai.mistralai")
public class MistralAiProperties {
    public static final String CONFIG_PREFIX = "spring.ai.mistralai";
    public static final String DEFAULT_BASE_URL = "https://api.mistral.ai";

    private String apiKey;
    private String baseUrl;

    public MistralAiProperties() {
        setBaseUrl(DEFAULT_BASE_URL);
    }

    public String getApiKey() {
        return this.apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

}
