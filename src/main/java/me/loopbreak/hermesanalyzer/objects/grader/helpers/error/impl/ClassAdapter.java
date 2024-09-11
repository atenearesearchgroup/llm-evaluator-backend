package me.loopbreak.hermesanalyzer.objects.grader.helpers.error.impl;

//import me.loopbreak.hermesanalyzer.objects.grader.dto.MissedModelElement;

import ca.mcgill.sel.grading.marks.MissedModelElement;
import me.loopbreak.hermesanalyzer.objects.grader.ModelError;
import me.loopbreak.hermesanalyzer.objects.grader.helpers.error.ErrorAdapter;

public class ClassAdapter implements ErrorAdapter {
    @Override
    public ModelError adaptError(MissedModelElement element) {

        return new ModelError(element.getMissedElementName(), "missing", element.getMissedElementName());
    }
}
