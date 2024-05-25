package me.loopbreak.hermesanalyzer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "intent_instance")
public class IntentInstanceEntity extends EvaluationSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String displayName;

    @ManyToOne
    @JoinColumn(name = "intent_model_id")
    private IntentModelEntity intentModel;

    @JsonIgnoreProperties("intentInstance")
    @OneToMany(mappedBy = "intentInstance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DraftEntity> drafts;

    private String platform;

    @OneToOne(mappedBy = "instance", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("instance")
    private ModelSettingsEntity modelSettings;

    public IntentInstanceEntity() {
    }

    public IntentInstanceEntity(String platform, String displayName, IntentModelEntity intentModel, EvaluationSettings evaluationSettings) {
        this.platform = platform;
        this.displayName = displayName;
        this.intentModel = intentModel;
        this.copy(evaluationSettings);
        this.drafts = new ArrayList<>();
    }

    public IntentInstanceEntity(IntentInstanceEntity instanceEntity) {
        this.platform = instanceEntity.platform;
        this.displayName = instanceEntity.displayName;
        this.intentModel = instanceEntity.intentModel;
        this.modelSettings = new ModelSettingsEntity();
        this.modelSettings.setInstance(this);
        this.copy(instanceEntity);
        this.drafts = new ArrayList<>();
        instanceEntity.getDrafts().forEach(draft -> this.drafts.add(draft.clone(this)));
    }

    public Long getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public IntentInstanceEntity clone() {
        return new IntentInstanceEntity(this);
    }
}