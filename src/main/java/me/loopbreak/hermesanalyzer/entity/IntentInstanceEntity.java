package me.loopbreak.hermesanalyzer.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "intent_instance")
public class IntentInstanceEntity extends EvaluationSettings {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "intent_model_id")
    private IntentModelEntity intentModel;

    @OneToMany(mappedBy = "intentInstance")
    private List<DraftEntity> drafts;

    private String platform;

    @OneToOne
    @JoinColumn(name = "model_settings_id")
    private ModelSettingsEntity modelSettings;

    public IntentInstanceEntity() {
    }

    public IntentInstanceEntity(String platform, IntentModelEntity intentModel, EvaluationSettings evaluationSettings, ModelSettingsEntity modelSettings) {
        this.platform = platform;
        this.intentModel = intentModel;
        this.modelSettings = modelSettings;
        this.copy(evaluationSettings);


    }

    public Long getId() {
        return id;
    }

    public String getPlatform() {
        return platform;
    }

    public ModelSettingsEntity getModelSettings() {
        return modelSettings;
    }

    public void setModelSettings(ModelSettingsEntity modelSettings) {
        this.modelSettings = modelSettings;
    }

    public IntentModelEntity getIntentModel() {
        return intentModel;
    }

    public List<DraftEntity> getDrafts() {
        return drafts;
    }
}