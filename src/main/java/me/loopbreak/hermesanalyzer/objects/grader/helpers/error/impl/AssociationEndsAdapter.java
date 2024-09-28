package me.loopbreak.hermesanalyzer.objects.grader.helpers.error.impl;

import ca.mcgill.sel.classdiagram.ClassDiagram;
import ca.mcgill.sel.grading.marks.MissedModelElement;
import me.loopbreak.hermesanalyzer.objects.grader.ModelError;
import me.loopbreak.hermesanalyzer.objects.grader.helpers.error.ErrorAdapter;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AssociationEndsAdapter implements ErrorAdapter {
    //    association Car_Service from Car to Service
    private static final String MISSING_ASSOC_REGEX = "(\\w+\\.\\w+)\\s.*";

    //"Car.services (lost 1.0 points)"
    @Nullable
    private String[] getMissingAssociationElements(String content) {
        Matcher matcher = Pattern.compile(MISSING_ASSOC_REGEX).matcher(content);
        if (matcher.find()) {
            String[] split = matcher.group(1).split("\\.");
            return new String[]{split[0], split[1]};
        }
        return null;
    }

    @Override
    public ModelError adaptError(MissedModelElement element, ClassDiagram solution) {
        String[] value = getMissingAssociationElements(element.getMissedElementName());

        if (value != null) {
            return new ModelError(element.getMissedElementName(), "missing", value);
        }

        return new ModelError(element.getMissedElementName(), "missing", element.getMissedElementName());
    }
}
