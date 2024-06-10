package me.loopbreak.hermesanalyzer.objects.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import me.loopbreak.hermesanalyzer.entity.ChatEntity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdateChatRequest(String actualNode) {
    public void update(ChatEntity chatEntity) {
        if (actualNode() != null) {
            chatEntity.setActualNode(actualNode());
        }
    }
}
