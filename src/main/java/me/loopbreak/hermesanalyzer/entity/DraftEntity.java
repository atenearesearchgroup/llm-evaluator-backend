package me.loopbreak.hermesanalyzer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import me.loopbreak.hermesanalyzer.entity.messages.PromptIterationEntity;
import me.loopbreak.hermesanalyzer.objects.draft.Draft;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "draft")
public class DraftEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JsonIgnoreProperties("drafts")
    @JoinColumn(name = "instance_id")
    private IntentInstanceEntity intentInstance;

    @JsonIgnoreProperties("draft")
    @OneToMany(mappedBy = "draft", cascade = CascadeType.ALL)
    private List<PromptIterationEntity> promptIterations;

    private int draftNumber;
    private boolean finalized;
    private String actualNode;

    public DraftEntity() {
    }

    public DraftEntity(IntentInstanceEntity intentInstance, int draftNumber) {
        this.intentInstance = intentInstance;
        this.draftNumber = draftNumber;
        this.promptIterations = new ArrayList<>();
        this.finalized = false;
        this.actualNode = null;
    }

    private DraftEntity(IntentInstanceEntity instanceEntity, DraftEntity draft) {
        this.intentInstance = instanceEntity;
        this.draftNumber = draft.getDraftNumber();
        this.promptIterations = new ArrayList<>();
        this.finalized = draft.isFinalized();
        this.actualNode = draft.getActualNode();
        this.promptIterations = new ArrayList<>();
        draft.getPromptIterations().forEach(promptIteration -> this.promptIterations.add(promptIteration.clone(this)));
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

    public boolean isFinalized() {
        return finalized;
    }

    public void setFinalized(boolean finalized) {
        this.finalized = finalized;
    }

    public String getActualNode() {
        return actualNode;
    }

    public void setActualNode(String actualNode) {
        this.actualNode = actualNode;
    }

    @JsonIgnore
    public Draft toDraft() {
        Draft draft = new Draft(draftNumber);
//        for (PromptIterationEntity promptIteration : promptIterations) {
//            draft.getHistory().add(promptIteration.toPromptPhase());
//        }
        return draft;
    }

    @Nullable
    @JsonIgnore
    public PromptIterationEntity getLastIteration() {
        return getPromptIterations()
                .stream().max(Comparator.comparing(PromptIterationEntity::getIteration))
                .orElse(null);
    }

    public DraftEntity clone(IntentInstanceEntity intentInstanceEntity) {
        return new DraftEntity(intentInstanceEntity, this);
    }
}