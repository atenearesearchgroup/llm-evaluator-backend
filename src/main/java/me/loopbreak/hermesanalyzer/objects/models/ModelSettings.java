package me.loopbreak.hermesanalyzer.objects.models;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelSettings implements ModelSettingsLike {
    private String modelName;
    //    replicate needed modelOwner for official models
    private String modelOwner;
    //    replicate version identifies the model itself
    private String version;
    private String systemPrompt;
    private int maxTokens= -1;
    private float temperature = -1;
    private float topP = -1;
//    private float repetitionPenalty;
    private float frequencyPenalty = -1;
    private float presencePenalty = -1;

    public ModelSettings(String modelName, String modelOwner, String version, String systemPrompt,
                         int maxTokens, float temperature, float topP,
                         float frequencyPenalty, float presencePenalty) {
        this.modelName = modelName;
        this.modelOwner = modelOwner;
        this.version = version;
        this.systemPrompt = systemPrompt;
        this.maxTokens = maxTokens;
        this.temperature = temperature;
        this.topP = topP;
        this.frequencyPenalty = frequencyPenalty;
        this.presencePenalty = presencePenalty;
    }

    public ModelSettings() {
//        jpa only
    }

    public String getModelName() {
        return modelName;
    }

    public String getModelOwner() {
        return modelOwner;
    }

    public String getVersion() {
        return version;
    }

    public String getSystemPrompt() {
        return systemPrompt;
    }

    public int getMaxTokens() {
        return maxTokens;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getTopP() {
        return topP;
    }

    public float getFrequencyPenalty() {
        return frequencyPenalty;
    }

    public float getPresencePenalty() {
        return presencePenalty;
    }

    public ModelSettingsBuilder toBuilder() {
        return new ModelSettingsBuilder(this);
    }

    public static ModelSettingsBuilder builder() {
        return new ModelSettingsBuilder();
    }

    @Override
    public ModelSettings asModelSettings() {
        return this;
    }
}
