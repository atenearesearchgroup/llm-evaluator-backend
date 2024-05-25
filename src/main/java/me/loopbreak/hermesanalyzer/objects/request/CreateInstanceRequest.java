package me.loopbreak.hermesanalyzer.objects.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import me.loopbreak.hermesanalyzer.entity.EvaluationSettings;
import me.loopbreak.hermesanalyzer.objects.models.ModelSettings;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreateInstanceRequest(String platform, String displayName, ModelSettings modelSettings,
                                    EvaluationSettings evaluationSettings) {

}
