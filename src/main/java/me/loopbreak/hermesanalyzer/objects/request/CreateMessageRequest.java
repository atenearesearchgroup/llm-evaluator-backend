package me.loopbreak.hermesanalyzer.objects.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import me.loopbreak.hermesanalyzer.entity.messages.AIMessageEntity;
import me.loopbreak.hermesanalyzer.entity.messages.MessageEntity;
import me.loopbreak.hermesanalyzer.entity.messages.PromptIterationEntity;
import me.loopbreak.hermesanalyzer.entity.messages.UserMessageEntity;

import java.sql.Timestamp;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreateMessageRequest(String promptType, String content, Integer score, Boolean manual) {

    public MessageEntity toMessageEntity(PromptIterationEntity promptIteration) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return score != null ?
                new AIMessageEntity(content, timestamp, score, manual != null ? manual : false, promptIteration) :
                new UserMessageEntity(content, timestamp, promptIteration);
    }

    public String getMessageType() {
        return score != null ? "AI" : "User";
    }

}
