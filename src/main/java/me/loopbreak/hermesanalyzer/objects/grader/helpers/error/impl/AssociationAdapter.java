package me.loopbreak.hermesanalyzer.objects.grader.helpers.error.impl;

import ca.mcgill.sel.grading.marks.MissedModelElement;
import me.loopbreak.hermesanalyzer.objects.grader.ModelError;
import me.loopbreak.hermesanalyzer.objects.grader.helpers.error.ErrorAdapter;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AssociationAdapter implements ErrorAdapter {
    //    association Car_Service from Car to Service
    private static final String MISSING_ASSOC_REGEX = "association\\s(\\w+)\\sfrom\\s(\\w+)\\sto\\s(\\w+)";

    @Nullable
    private String[] getMissingAssociationElements(String content) {
        Matcher matcher = Pattern.compile(MISSING_ASSOC_REGEX).matcher(content);
        if (matcher.find()) {
            return new String[]{matcher.group(1), matcher.group(2), matcher.group(3)};
        }
        return null;
    }

    @Override
    public ModelError adaptError(MissedModelElement element) {

        String[] value = getMissingAssociationElements(element.getMissedElementName());

        if (value != null) {
            return new ModelError(value[0], "missing", value[1], value[2]);
        }

        return new ModelError(element.getMissedElementName(), "missing", element.getMissedElementName());
    }
}
