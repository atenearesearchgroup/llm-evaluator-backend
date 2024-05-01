package me.loopbreak.hermesanalyzer.objects.draft.messages;

import java.sql.Timestamp;

public class UserMessage extends Message {
    public UserMessage(String content, Timestamp timestamp) {
        super(content, timestamp);
    }

    @Override
    public org.springframework.ai.chat.messages.Message toSpringMessage() {
        return new org.springframework.ai.chat.messages.UserMessage(getContent());
    }

    @Override
    public String toString() {
        return "UserMessage{" +
               "content='" + getContent() + '\'' +
               ", timestamp=" + getTimestamp() +
               '}';
    }
}
