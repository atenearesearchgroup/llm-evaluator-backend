package me.loopbreak.hermesanalyzer.objects.evaluator.helpers.error;

import me.loopbreak.hermesanalyzer.objects.evaluator.dto.MissedModelElement;
import me.loopbreak.hermesanalyzer.objects.evaluator.response.ModelError;

public interface ErrorAdapter {

    ModelError adaptError(MissedModelElement element);

}
