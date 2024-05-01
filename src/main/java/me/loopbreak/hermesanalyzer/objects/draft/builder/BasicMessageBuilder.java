package me.loopbreak.hermesanalyzer.objects.draft.builder;


import me.loopbreak.hermesanalyzer.objects.draft.messages.Message;
import org.springframework.lang.NonNull;

public abstract class BasicMessageBuilder<T extends Message> implements MessageBuilder<T> {

    private String content;
    private long timestamp;

    protected String getContent() {
        return content;
    }

    protected long getTimestamp() {
        return timestamp;
    }

    @Override
    public MessageBuilder<T> content(@NonNull String content) {
        this.content = content;
        return this;
    }

    @Override
    public MessageBuilder<T> timestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
