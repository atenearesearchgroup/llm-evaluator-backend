package me.loopbreak.hermesanalyzer.objects.grader.helpers.error.impl;

import ca.mcgill.sel.classdiagram.ClassDiagram;
import ca.mcgill.sel.grading.marks.MissedModelElement;
import me.loopbreak.hermesanalyzer.objects.grader.ModelError;
import me.loopbreak.hermesanalyzer.objects.grader.helpers.error.ErrorAdapter;

public class DummyAdapter implements ErrorAdapter {
    @Override
    public ModelError adaptError(MissedModelElement element, ClassDiagram solution) {
        return new ModelError(element.getMissedElementName(), "missing", element.getMissedElementName());
    }
}
