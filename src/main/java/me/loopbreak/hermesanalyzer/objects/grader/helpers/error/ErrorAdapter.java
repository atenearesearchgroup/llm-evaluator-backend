package me.loopbreak.hermesanalyzer.objects.grader.helpers.error;

import ca.mcgill.sel.classdiagram.ClassDiagram;
import ca.mcgill.sel.grading.marks.MissedModelElement;
import me.loopbreak.hermesanalyzer.objects.grader.ModelError;

public interface ErrorAdapter {

    ModelError adaptError(MissedModelElement element, ClassDiagram solution);

}
