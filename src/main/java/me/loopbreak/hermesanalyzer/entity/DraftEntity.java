package me.loopbreak.hermesanalyzer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import me.loopbreak.hermesanalyzer.entity.messages.PromptIterationEntity;

import java.util.List;

@Entity
@Table(name = "draft")
public class DraftEntity {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JsonIgnoreProperties("drafts")
    @JoinColumn(name = "instance_id")
    private IntentInstanceEntity intentInstance;

    @JsonIgnoreProperties("draft")
    @OneToMany(mappedBy = "draft")
    private List<PromptIterationEntity> promptIterations;

    private int draftNumber;

    public DraftEntity() {
    }

    public DraftEntity(IntentInstanceEntity intentInstance, int draftNumber) {
        this.intentInstance = intentInstance;
        this.draftNumber = draftNumber;
    }

    public long getId() {
        return id;
    }

    public IntentInstanceEntity getIntentInstance() {
        return intentInstance;
    }

    public List<PromptIterationEntity> getPromptIterations() {
        return promptIterations;
    }

    public int getDraftNumber() {
        return draftNumber;
    }
}