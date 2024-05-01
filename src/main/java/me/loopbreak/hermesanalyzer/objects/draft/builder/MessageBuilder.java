package me.loopbreak.hermesanalyzer.objects.draft.builder;

import me.loopbreak.hermesanalyzer.objects.draft.messages.Message;
import org.springframework.lang.NonNull;

public interface MessageBuilder<T extends Message> {

    T build();

    MessageBuilder<T> content(@NonNull String content);

    MessageBuilder<T> timestamp(long timestamp);

    default MessageBuilder<T> setTimestampNow() {
        return timestamp(System.currentTimeMillis());
    }
}
