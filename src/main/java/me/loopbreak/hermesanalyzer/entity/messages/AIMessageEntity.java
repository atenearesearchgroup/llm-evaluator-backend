package me.loopbreak.hermesanalyzer.entity.messages;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.sql.Timestamp;

@Entity
@DiscriminatorValue("ai")
public class AIMessageEntity extends MessageEntity {

    private int score;

    public AIMessageEntity() {
    }

    public AIMessageEntity(String content, Timestamp timestamp, int score) {
        super(content, timestamp);
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
