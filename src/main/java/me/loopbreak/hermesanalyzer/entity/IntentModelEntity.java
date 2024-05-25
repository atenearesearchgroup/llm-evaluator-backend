package me.loopbreak.hermesanalyzer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "intent_model")
public class IntentModelEntity {

    @Id
    private String modelName;

    private String displayName;

    public IntentModelEntity() {
    }

    public IntentModelEntity(String modelName, String displayName) {
        this.modelName = modelName;
        this.displayName = displayName;
    }

    public String getModelName() {
        return modelName;
    }

    public String getDisplayName() {
        return displayName;
    }

}