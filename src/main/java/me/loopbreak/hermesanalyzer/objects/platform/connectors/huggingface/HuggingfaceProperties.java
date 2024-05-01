package me.loopbreak.hermesanalyzer.objects.platform.connectors.huggingface;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.ai.huggingface.chat")
public class HuggingfaceProperties {
    public static final String CONFIG_PREFIX = "spring.ai.huggingface.chat";
    private String apiKey;
    private String url;
    private boolean enabled = true;

    public HuggingfaceProperties() {
    }

    public String getApiKey() {
        return this.apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

