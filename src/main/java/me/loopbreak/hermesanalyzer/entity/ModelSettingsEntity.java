package me.loopbreak.hermesanalyzer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import me.loopbreak.hermesanalyzer.objects.models.ModelSettings;
import me.loopbreak.hermesanalyzer.objects.models.ModelSettingsBuilder;
import me.loopbreak.hermesanalyzer.objects.models.ModelSettingsLike;

@Entity
@Table(name = "model_settings")
public class ModelSettingsEntity implements ModelSettingsLike {

    @Id
    private Long id;

    @JsonIgnore
    @MapsId
    @OneToOne
    @JoinColumn(name = "id")
    private IntentInstanceEntity instance;

    private String modelName;
    //    replicate needed modelOwner for official models
    private String modelOwner;
    //    replicate version identifies the model itself
    private String version;
    private String systemPrompt;
    @Column(columnDefinition = "integer default -1")
    private int maxTokens;
    @Column(columnDefinition = "float default -1")
    private float temperature;
    @Column(columnDefinition = "float default -1")
    private float topP;
    //    private float repetitionPenalty;
    @Column(columnDefinition = "float default -1")
    private float frequencyPenalty;
    @Column(columnDefinition = "float default -1")
    private float presencePenalty;

    public ModelSettingsEntity() {
    }

    public ModelSettingsEntity(ModelSettingsLike modelSettingsLike) {
        this(modelSettingsLike.asModelSettings());
    }

    public ModelSettingsEntity(ModelSettings modelSettings) {
        this(modelSettings.getModelName(), modelSettings.getModelOwner(), modelSettings.getVersion(),
                modelSettings.getSystemPrompt(), modelSettings.getMaxTokens(), modelSettings.getTemperature(),
                modelSettings.getTopP(), modelSettings.getFrequencyPenalty(), modelSettings.getPresencePenalty());
    }

    public ModelSettingsEntity(String modelName, String modelOwner, String version, String systemPrompt,
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


    public Long getId() {
        return id;
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


    public IntentInstanceEntity getInstance() {
        return instance;
    }

    public void setInstance(IntentInstanceEntity instance) {
        this.instance = instance;
    }

    @Override
    public ModelSettings asModelSettings() {
        ModelSettingsBuilder builder = ModelSettings.builder();

        builder.modelName(modelName)
                .modelOwner(modelOwner)
                .version(version)
                .systemPrompt(systemPrompt)
                .maxTokens(maxTokens)
                .temperature(temperature)
                .topP(topP)
                .frequencyPenalty(frequencyPenalty)
                .presencePenalty(presencePenalty);

        return builder.asModelSettings();
    }

    public void copy(ModelSettingsLike modelSettingsLike) {
        ModelSettings modelSettings = modelSettingsLike.asModelSettings();

        modelName = modelSettings.getModelName();
        modelOwner = modelSettings.getModelOwner();
        version = modelSettings.getVersion();
        systemPrompt = modelSettings.getSystemPrompt();
        maxTokens = modelSettings.getMaxTokens();
        temperature = modelSettings.getTemperature();
        topP = modelSettings.getTopP();
        frequencyPenalty = modelSettings.getFrequencyPenalty();
        presencePenalty = modelSettings.getPresencePenalty();
    }
}
