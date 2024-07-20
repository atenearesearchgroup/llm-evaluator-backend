package me.loopbreak.hermesanalyzer.entity.messages;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import me.loopbreak.hermesanalyzer.objects.draft.messages.Message;
import me.loopbreak.hermesanalyzer.objects.draft.messages.UserMessage;

import java.sql.Timestamp;

@Entity
@DiscriminatorValue("user")
public class UserMessageEntity extends MessageEntity {

    public UserMessageEntity() {
    }

    public UserMessageEntity(String content, Timestamp timestamp, PromptIterationEntity promptIteration) {
        super(content, timestamp, promptIteration);
    }

    public UserMessageEntity(PromptIterationEntity promptIterationEntity, UserMessageEntity userMessageEntity) {
        super(userMessageEntity.getContent(), userMessageEntity.getTimestamp(), promptIterationEntity);
    }

    public String getType() {
        return "user";
    }


    @Override
    public UserMessageEntity clone(PromptIterationEntity promptIterationEntity) {
        return new UserMessageEntity(promptIterationEntity, this);
    }

    @Override
    public Message toMessage() {
        return new UserMessage(getContent(), getTimestamp());
    }
}
