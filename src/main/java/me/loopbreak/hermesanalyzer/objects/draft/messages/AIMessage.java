package me.loopbreak.hermesanalyzer.objects.draft.messages;

import me.loopbreak.hermesanalyzer.exceptions.InvalidModelOutputException;
import me.loopbreak.hermesanalyzer.utils.PlantParser;
import org.springframework.ai.chat.messages.AssistantMessage;

import java.sql.Timestamp;

public class AIMessage extends Message {

    private double score;

    public AIMessage(String content, double score, Timestamp timestamp) {
        super(content, timestamp);
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    /**
     * @param score The score to be set 0-100
     */
    public void setScore(double score) {
        this.score = score;
    }

    public String getOutputDiagram() throws InvalidModelOutputException {
        return PlantParser.getPlantUML(getContent());
    }

    @Override
    public org.springframework.ai.chat.messages.Message toSpringMessage() {
        return new AssistantMessage(getContent());
    }

    @Override
    public String toString() {
        return "AIMessage{" +
               "content='" + getContent() + '\'' +
               ", score=" + score +
               ", timestamp=" + getTimestamp() +
               '}';
    }
}
