package me.loopbreak.hermesanalyzer.objects.grader.helpers.error.impl;

import ca.mcgill.sel.classdiagram.Attribute;
import ca.mcgill.sel.classdiagram.ClassDiagram;
import ca.mcgill.sel.grading.marks.Mark;
import ca.mcgill.sel.grading.marks.MissedModelElement;
import ca.mcgill.sel.grading.marks.impl.MarksMapImpl;
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
    public ModelError adaptError(MissedModelElement element, ClassDiagram solution) {
        String[] value = getElements(element.getMissedElementName());

        if (value != null) {
            return new ModelError(element.getMissedElementName(), "missing", value[0], value[1]);
        }

        return new ModelError(element.getMissedElementName(), "missing", element.getMissedElementName());
    }

    public ModelError adaptError(Mark mark, ClassDiagram solution) {
        String comment = mark.getComment();
        if (comment.startsWith("Also equivalent to")) return null;

        if (!comment.contains("Wrong type")) return null;

        String[] methodClass = comment.substring(comment.indexOf("attribute") + 10, comment.indexOf(". Wrong type")).split("\\.");

        if (methodClass.length != 2) throw new RuntimeException("Invalid comment format " + comment);

        String className = methodClass[0];
        String attributeName = methodClass[1];

        String type = ((Attribute) ((MarksMapImpl) mark.eContainer()).getKey()).getType().getName();

        return new ModelError(className + "." + attributeName, "wrong_type", className, attributeName, type);
    }
}
