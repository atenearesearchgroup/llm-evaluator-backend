package me.loopbreak.hermesanalyzer.objects.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import me.loopbreak.hermesanalyzer.entity.EvaluationSettings;
import me.loopbreak.hermesanalyzer.entity.IntentInstanceEntity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdateInstanceRequest(String displayName, EvaluationSettings evaluationSettings) {

    public void update(IntentInstanceEntity intentInstanceEntity) {
        if (displayName() != null)
            intentInstanceEntity.setDisplayName(displayName());

        if (evaluationSettings() != null)
            intentInstanceEntity.copy(evaluationSettings());
    }
}
