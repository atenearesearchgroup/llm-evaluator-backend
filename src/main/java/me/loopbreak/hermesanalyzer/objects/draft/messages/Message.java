package me.loopbreak.hermesanalyzer.objects.draft.messages;

import me.loopbreak.hermesanalyzer.objects.draft.builder.AIMessageBuilder;
import me.loopbreak.hermesanalyzer.objects.draft.builder.BasicMessageBuilder;
import me.loopbreak.hermesanalyzer.objects.draft.builder.MessageBuilder;

import java.sql.Timestamp;

public abstract class Message implements Comparable<Message> {
    private String content;
    private Timestamp timestamp;

    protected Message(String content, Timestamp timestamp) {
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public abstract org.springframework.ai.chat.messages.Message toSpringMessage();

    public static MessageBuilder<UserMessage> userMessage() {
        return new BasicMessageBuilder<>() {
            @Override
            public UserMessage build() {
                return new UserMessage(getContent(), new Timestamp(getTimestamp()));
            }
        };
    }

    public static AIMessageBuilder aiMessage() {
        return new AIMessageBuilder();
    }

    @Override
    public int compareTo(Message o) {
        return getTimestamp().compareTo(o.getTimestamp());
    }
}
