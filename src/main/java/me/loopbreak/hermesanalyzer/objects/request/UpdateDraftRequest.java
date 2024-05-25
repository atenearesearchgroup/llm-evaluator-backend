package me.loopbreak.hermesanalyzer.objects.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import me.loopbreak.hermesanalyzer.entity.DraftEntity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdateDraftRequest(String actualNode) {
    public void update(DraftEntity draftEntity) {
        if (actualNode() != null) {
            draftEntity.setActualNode(actualNode());
        }
    }
}
