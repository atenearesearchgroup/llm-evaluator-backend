package me.loopbreak.hermesanalyzer.objects.grader.helpers.error.impl;

import ca.mcgill.sel.classdiagram.CDEnum;
import ca.mcgill.sel.classdiagram.ClassDiagram;
import ca.mcgill.sel.classdiagram.Type;
import ca.mcgill.sel.grading.marks.MissedModelElement;
import me.loopbreak.hermesanalyzer.objects.grader.ModelError;
import me.loopbreak.hermesanalyzer.objects.grader.helpers.error.ErrorAdapter;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class EnumAdapter implements ErrorAdapter {


    @Override
    public ModelError adaptError(MissedModelElement element, ClassDiagram solution) {
        String[] split = element.getMissedElementName().split(" ", 2);

        if (split.length > 0) {
            String enumName = split[0];

            CDEnum enumElement = getEnum(solution, enumName);

            if (enumElement == null)
                return new ModelError(element.getMissedElementName(), "missing", enumName);

            split = Arrays.copyOf(split, 1 + enumElement.getLiterals().size());
            split[0] = enumName;

            for (int i = 0; i < enumElement.getLiterals().size(); i++) {
                split[i + 1] = enumElement.getLiterals().get(i).getName();
            }

            return new ModelError(element.getMissedElementName(), "missing", split);
        }

        return new ModelError(element.getMissedElementName(), "missing", element.getMissedElementName());
    }

    @Nullable
    private CDEnum getEnum(ClassDiagram solution, String enumName) {
        for (Type type : solution.getTypes()) {
            if (!(type instanceof CDEnum cdEnum))
                continue;

            if (cdEnum.getName().equals(enumName)) {
                return cdEnum;
            }
        }
        return null;
    }
}
