package me.loopbreak.hermesanalyzer.objects.grader.helpers.error;

import ca.mcgill.sel.classdiagram.ClassDiagram;
import ca.mcgill.sel.grading.marks.Mark;
import ca.mcgill.sel.grading.marks.MissedModelElement;
import me.loopbreak.hermesanalyzer.objects.grader.ModelError;
import org.jetbrains.annotations.Nullable;

public interface ErrorAdapter {

    ModelError adaptError(MissedModelElement element, ClassDiagram solution);

    @Nullable
    default ModelError adaptError(Mark mark, ClassDiagram solution) {
        return null;
    }

}
