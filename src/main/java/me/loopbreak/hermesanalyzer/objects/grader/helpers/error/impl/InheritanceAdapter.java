package me.loopbreak.hermesanalyzer.objects.grader.helpers.error.impl;

//import me.loopbreak.hermesanalyzer.objects.grader.dto.MissedModelElement;

import ca.mcgill.sel.classdiagram.ClassDiagram;
import ca.mcgill.sel.grading.marks.MissedModelElement;
import me.loopbreak.hermesanalyzer.objects.grader.ModelError;
import me.loopbreak.hermesanalyzer.objects.grader.helpers.error.ErrorAdapter;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InheritanceAdapter implements ErrorAdapter {
    private static final String PATTERN = "Inheritance between (.*) and (.*) \\(lost.*";

    @Nullable
    private String[] getElements(String content) {
        Matcher matcher = Pattern.compile(PATTERN).matcher(content);
        if (matcher.find()) {
            return new String[]{matcher.group(1), matcher.group(2)};
        }
        return null;
    }

    @Override
    public ModelError adaptError(MissedModelElement element, ClassDiagram solution) {
        String[] value = getElements(element.getMissedElementName());

        if (value != null) {
            return new ModelError(element.getMissedElementName(), "missing", value[0], value[1]);
        }

        return new ModelError(element.getMissedElementName(), "missing", element.getMissedElementName());
    }
}
