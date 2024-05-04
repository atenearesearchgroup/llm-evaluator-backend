package me.loopbreak.hermesanalyzer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "intent_model")
public class IntentModelEntity {

    @Id
    private String modelName;

    public String getModelName() {
        return modelName;
    }

}