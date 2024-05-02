package me.loopbreak.hermesanalyzer.entity.messages;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("user")
public class UserMessageEntity extends MessageEntity {

    public UserMessageEntity() {
    }

    public UserMessageEntity(String content) {
        super(content);
    }
}
