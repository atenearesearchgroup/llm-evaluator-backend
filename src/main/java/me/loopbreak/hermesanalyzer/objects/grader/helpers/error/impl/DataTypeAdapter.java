package me.loopbreak.hermesanalyzer.objects.grader.helpers.error.impl;

//import me.loopbreak.hermesanalyzer.objects.grader.dto.MissedModelElement;

import ca.mcgill.sel.classdiagram.ClassDiagram;
import ca.mcgill.sel.grading.marks.MissedModelElement;
import me.loopbreak.hermesanalyzer.objects.grader.ModelError;
import me.loopbreak.hermesanalyzer.objects.grader.helpers.error.ErrorAdapter;

public class DataTypeAdapter implements ErrorAdapter {
    @Override
    public ModelError adaptError(MissedModelElement element, ClassDiagram solution) {
        String[] split = element.getMissedElementName().split(" ");

        String className = split[0];

        return new ModelError(element.getMissedElementName(), "missing", className);
    }
}
