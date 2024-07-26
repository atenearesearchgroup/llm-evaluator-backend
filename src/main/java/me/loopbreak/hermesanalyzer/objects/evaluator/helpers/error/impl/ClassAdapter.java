package me.loopbreak.hermesanalyzer.objects.evaluator.helpers.error.impl;

import me.loopbreak.hermesanalyzer.objects.evaluator.dto.MissedModelElement;
import me.loopbreak.hermesanalyzer.objects.evaluator.helpers.error.ErrorAdapter;
import me.loopbreak.hermesanalyzer.objects.evaluator.response.ModelError;

public class ClassAdapter implements ErrorAdapter {
    @Override
    public ModelError adaptError(MissedModelElement element) {

        return new ModelError(element.getMissedElementName(), "missing", element.getMissedElementName());
    }
}
