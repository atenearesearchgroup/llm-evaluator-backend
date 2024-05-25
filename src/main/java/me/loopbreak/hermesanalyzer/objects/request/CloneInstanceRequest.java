package me.loopbreak.hermesanalyzer.objects.request;

import me.loopbreak.hermesanalyzer.entity.EvaluationSettings;
import me.loopbreak.hermesanalyzer.entity.IntentInstanceEntity;
import me.loopbreak.hermesanalyzer.objects.models.ModelSettings;

public record CloneInstanceRequest(EvaluationSettings evaluationSettings, ModelSettings modelSettings) {
    public void apply(IntentInstanceEntity instanceEntity) {
        if (evaluationSettings() != null) {
            instanceEntity.copy(evaluationSettings());
        }
        if (modelSettings() != null) {
            instanceEntity.getModelSettings().copy(modelSettings());
        }
    }
}
