package me.loopbreak.hermesanalyzer.objects.grader.helpers.error.impl;

import ca.mcgill.sel.classdiagram.AssociationEnd;
import ca.mcgill.sel.classdiagram.Class;
import ca.mcgill.sel.classdiagram.ClassDiagram;
import ca.mcgill.sel.grading.marks.Mark;
import ca.mcgill.sel.grading.marks.MissedModelElement;
import ca.mcgill.sel.grading.marks.impl.MarksMapImpl;
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

    @Override
    public @Nullable ModelError adaptError(Mark mark, ClassDiagram solution) {
        String comment = mark.getComment();
        if (!comment.contains("Wrong bound")) return null;

        String[] methodClass = comment.substring(comment.indexOf("association end") + 16, comment.indexOf(". Wrong bounds")).split("\\.");

        if (methodClass.length != 2) throw new RuntimeException("Invalid comment format " + comment);
        String endName = methodClass[1];

        String bounds = comment.substring(comment.indexOf("should be ") + 11, comment.indexOf(", hence"));

        AssociationEnd association = (AssociationEnd) ((MarksMapImpl) mark.eContainer()).getKey();

        String mainClass = ((Class) association.eContainer()).getName();
        String oppositeClass = ((Class) association.getOppositeEnd().eContainer()).getName();

        return new ModelError(mainClass + "-" + oppositeClass + "." + endName, "wrong_bound", mainClass, oppositeClass, endName, bounds);
    }
}
