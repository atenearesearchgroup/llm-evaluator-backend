package me.loopbreak.hermesanalyzer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "intent_model_entity")
public class IntentModelEntity {

    @Id
    private String modelName;

    public String getModelName() {
        return modelName;
    }

}