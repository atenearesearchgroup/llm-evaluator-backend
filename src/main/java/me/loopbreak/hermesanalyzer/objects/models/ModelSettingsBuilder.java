package me.loopbreak.hermesanalyzer.objects.models;

import me.loopbreak.hermesanalyzer.exceptions.ModelSettingsBuilderException;

public class ModelSettingsBuilder implements ModelSettingsLike {
    private String modelName = null;
    private String modelOwner = null;
    private String version = null;
    private String systemPrompt = null;
    private int maxTokens = -1;
    private float temperature = -1;
    private float topP = -1;
    private float frequencyPenalty = -1;
    private float presencePenalty = -1;

    ModelSettingsBuilder() {
    }

    ModelSettingsBuilder(ModelSettings settings) {
        this.modelName = settings.getModelName();
        this.modelOwner = settings.getModelOwner();
        this.version = settings.getVersion();
        this.systemPrompt = settings.getSystemPrompt();
        this.maxTokens = settings.getMaxTokens();
        this.temperature = settings.getTemperature();
        this.topP = settings.getTopP();
        this.frequencyPenalty = settings.getFrequencyPenalty();
        this.presencePenalty = settings.getPresencePenalty();
    }

    public ModelSettingsBuilder modelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public ModelSettingsBuilder modelOwner(String modelOwner) {
        this.modelOwner = modelOwner;
        return this;
    }

    public ModelSettingsBuilder version(String version) {
        this.version = version;
        return this;
    }

    public ModelSettingsBuilder systemPrompt(String systemPrompt) {
        this.systemPrompt = systemPrompt;
        return this;
    }

    public ModelSettingsBuilder maxTokens(int maxTokens) {
        this.maxTokens = maxTokens;
        return this;
    }

    public ModelSettingsBuilder temperature(float temperature) {
        this.temperature = temperature;
        return this;
    }

    public ModelSettingsBuilder topP(float topP) {
        this.topP = topP;
        return this;
    }

    public ModelSettingsBuilder frequencyPenalty(float frequencyPenalty) {
        this.frequencyPenalty = frequencyPenalty;
        return this;
    }

    public ModelSettingsBuilder presencePenalty(float presencePenalty) {
        this.presencePenalty = presencePenalty;
        return this;
    }

    public ModelSettings build() {
        if (modelName == null) throw new ModelSettingsBuilderException(this, "Model name is not set");

        return new ModelSettings(modelName, modelOwner, version, systemPrompt, maxTokens, temperature, topP, frequencyPenalty, presencePenalty);
    }

    @Override
    public ModelSettings asModelSettings() {
        return build();
    }
}
