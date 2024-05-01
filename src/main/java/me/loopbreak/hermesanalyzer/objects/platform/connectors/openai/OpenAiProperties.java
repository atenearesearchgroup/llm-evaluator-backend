package me.loopbreak.hermesanalyzer.objects.platform.connectors.openai;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.ai.openai")
public class OpenAiProperties {
    public static final String CONFIG_PREFIX = "spring.ai.openai";
    public static final String DEFAULT_BASE_URL = "https://api.openai.com";

    private String apiKey;
    private String baseUrl;

    public OpenAiProperties() {
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
