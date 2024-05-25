package me.loopbreak.hermesanalyzer.entity.messages;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

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
}
