package me.loopbreak.hermesanalyzer.objects.grader.helpers.error;

import ca.mcgill.sel.grading.marks.MissedModelElement;
import me.loopbreak.hermesanalyzer.objects.grader.ModelError;

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

    public ModelError adaptError(String type, MissedModelElement element) {
        ErrorType errorType;
        try {
            errorType = ErrorType.valueOf(type);
        } catch (Exception exception) {
            errorType = ErrorType.DUMMY;
        }

        return errorType.getAdapter().adaptError(element);
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
