package me.loopbreak.hermesanalyzer.objects.grader.helpers.error.impl;

import ca.mcgill.sel.grading.marks.MissedModelElement;
import me.loopbreak.hermesanalyzer.objects.grader.ModelError;
import me.loopbreak.hermesanalyzer.objects.grader.helpers.error.ErrorAdapter;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AttributesAdapter implements ErrorAdapter {
    //    Car.plate
    private static final String MISSING_ATTR_REGEX = "(\\w+)\\.(\\w+)";

    @Nullable
    private String[] getElements(String content) {
        Matcher matcher = Pattern.compile(MISSING_ATTR_REGEX).matcher(content);
        if (matcher.find()) {
            return new String[]{matcher.group(1), matcher.group(2)};
        }
        return null;
    }

    @Override
    public ModelError adaptError(MissedModelElement element) {

        String[] value = getElements(element.getMissedElementName());

        if (value != null) {
            return new ModelError(element.getMissedElementName(), "missing", value[0], value[1]);
        }

        return new ModelError(element.getMissedElementName(), "missing", element.getMissedElementName());
    }
}
