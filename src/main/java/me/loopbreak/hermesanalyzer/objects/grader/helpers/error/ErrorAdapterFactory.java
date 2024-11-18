package me.loopbreak.hermesanalyzer.objects.grader.helpers.error;

import ca.mcgill.sel.classdiagram.ClassDiagram;
import ca.mcgill.sel.grading.marks.Mark;
import ca.mcgill.sel.grading.marks.MissedModelElement;
import me.loopbreak.hermesanalyzer.objects.grader.ModelError;
import org.jetbrains.annotations.Nullable;

public class ErrorAdapterFactory {

    private static ErrorAdapterFactory instance;

    private ErrorAdapterFactory() {
    }

    public static ErrorAdapterFactory getInstance() {
        if (instance == null) {
            instance = new ErrorAdapterFactory();
        }
        return instance;
    }

    public ModelError adaptError(String type, MissedModelElement element, ClassDiagram solution) {
        ErrorType errorType;
        try {
            errorType = ErrorType.valueOf(type);
        } catch (Exception exception) {
            errorType = ErrorType.DUMMY;
        }

        return errorType.getAdapter().adaptError(element, solution);
    }

    @Nullable
    public ModelError adaptError(String type, Mark element, ClassDiagram solution) {
        ErrorType errorType;
        try {
            errorType = ErrorType.valueOf(type);
        } catch (Exception exception) {
            errorType = ErrorType.DUMMY;
        }

        return errorType.getAdapter().adaptError(element, solution);
    }

   /* public ModelError adaptError(String type, MissedModelElement element) {
        ErrorType errorType;
        try {
            errorType = ErrorType.valueOf(type);
        } catch (Exception exception) {
            errorType = ErrorType.DUMMY;
        }

        return errorType.getAdapter().adaptError(element);
    }*/

}
