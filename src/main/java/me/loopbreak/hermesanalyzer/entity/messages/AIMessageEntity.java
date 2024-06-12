package me.loopbreak.hermesanalyzer.entity.messages;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.sql.Timestamp;

@Entity
@DiscriminatorValue("ai")
public class AIMessageEntity extends MessageEntity {

    private double score;
    @Column(name = "is_manual", columnDefinition = "boolean default false")
    private boolean manual;

    public AIMessageEntity() {
    }

    public AIMessageEntity(String content, Timestamp timestamp, double score, boolean manual, PromptIterationEntity promptIteration) {
        super(content, timestamp, promptIteration);
        this.score = score;
        this.manual = manual;
    }

    private AIMessageEntity(PromptIterationEntity promptIterationEntity, AIMessageEntity aiMessageEntity) {
        super(aiMessageEntity.getContent(), aiMessageEntity.getTimestamp(), promptIterationEntity);
        this.score = aiMessageEntity.getScore();
        this.manual = aiMessageEntity.isManual();
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public boolean isManual() {
        return manual;
    }

    public String getType() {
        return "ai";
    }

    @Override
    public AIMessageEntity clone(PromptIterationEntity promptIterationEntity) {
        return new AIMessageEntity(promptIterationEntity, this);
    }
}
