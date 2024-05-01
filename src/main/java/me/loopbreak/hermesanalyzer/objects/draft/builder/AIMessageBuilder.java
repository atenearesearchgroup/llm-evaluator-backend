package me.loopbreak.hermesanalyzer.objects.draft.builder;

import me.loopbreak.hermesanalyzer.objects.draft.messages.AIMessage;

import java.sql.Timestamp;

public class AIMessageBuilder extends BasicMessageBuilder<AIMessage> {

    private int score;

    public AIMessageBuilder score(int score) {
        this.score = score;
        return this;
    }

    @Override
    public AIMessage build() {
        return new AIMessage(getContent(), score, new Timestamp(getTimestamp()));
    }
}
