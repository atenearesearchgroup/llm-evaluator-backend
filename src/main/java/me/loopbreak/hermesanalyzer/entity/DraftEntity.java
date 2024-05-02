package me.loopbreak.hermesanalyzer.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "draft_entity")
public class DraftEntity {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "instance_id")
    private IntentInstanceEntity intentModel;

    private int draftNumber;

    public DraftEntity() {
    }

    public DraftEntity(IntentInstanceEntity intentModel, int draftNumber) {
        this.intentModel = intentModel;
        this.draftNumber = draftNumber;
    }

    public long getId() {
        return id;
    }

    public IntentInstanceEntity getIntentModel() {
        return intentModel;
    }

    public int getDraftNumber() {
        return draftNumber;
    }
}