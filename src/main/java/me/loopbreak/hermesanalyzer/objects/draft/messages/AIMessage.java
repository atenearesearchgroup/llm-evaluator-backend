package me.loopbreak.hermesanalyzer.objects.draft.messages;

import me.loopbreak.hermesanalyzer.exceptions.InvalidModelOutputException;
import me.loopbreak.hermesanalyzer.utils.PlantParser;
import org.springframework.ai.chat.messages.AssistantMessage;

import java.sql.Timestamp;

public class AIMessage extends Message {

    private int score;

    public AIMessage(String content, int score, Timestamp timestamp) {
        super(content, timestamp);
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    /**
     * TODO: Check if it would be better to be auto-evaluated by the own model and return with the score already
     *  or if it would be better to just receive the call and evaluate the output with the result.
     *
     * @param score The score to be set 0-100
     */
    public void setScore(int score) {
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
