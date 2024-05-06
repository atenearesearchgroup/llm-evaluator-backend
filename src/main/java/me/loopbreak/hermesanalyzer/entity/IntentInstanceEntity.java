package me.loopbreak.hermesanalyzer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "intent_instance")
public class IntentInstanceEntity extends EvaluationSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "intent_model_id")
    private IntentModelEntity intentModel;

    @JsonIgnoreProperties("intentInstance")
    @OneToMany(mappedBy = "intentInstance", cascade = CascadeType.ALL)
    private List<DraftEntity> drafts;

    private String platform;

    @OneToOne(mappedBy = "instance", cascade = CascadeType.ALL)
    private ModelSettingsEntity modelSettings;

    public IntentInstanceEntity() {
    }

    public IntentInstanceEntity(String platform, IntentModelEntity intentModel, EvaluationSettings evaluationSettings) {
        this.platform = platform;
        this.intentModel = intentModel;
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