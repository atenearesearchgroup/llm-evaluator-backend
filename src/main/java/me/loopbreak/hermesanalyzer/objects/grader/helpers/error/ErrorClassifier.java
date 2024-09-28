package me.loopbreak.hermesanalyzer.objects.grader.helpers.error;

import ca.mcgill.sel.classdiagram.ClassDiagram;
import ca.mcgill.sel.grading.marks.MarksModel;
import ca.mcgill.sel.grading.marks.MissedModelElement;
import ca.mcgill.sel.grading.marks.ModelElementCategory;
import me.loopbreak.hermesanalyzer.objects.grader.CategoryError;
import me.loopbreak.hermesanalyzer.objects.grader.ModelError;

import java.util.ArrayList;
import java.util.List;

public class ErrorClassifier {

    private MarksModel marksModel;
    private ClassDiagram solution = null;

    private ErrorClassifier(MarksModel marksModel) {
        this.marksModel = marksModel;
    }

    public static ErrorClassifier of(MarksModel marksModel) {
        return new ErrorClassifier(marksModel);
    }

    public List<CategoryError> classify() {
        if (solution == null)
            throw new IllegalStateException("Solution not set");

        List<CategoryError> categories = new ArrayList<>();

        for (ModelElementCategory modelElementCategory : marksModel.getModelElementCategories()) {
            CategoryError categoryError = new CategoryError(modelElementCategory.getName().toLowerCase()
                    .replace(" ", "_"), new ArrayList<>());

            for (MissedModelElement missedModelElement : modelElementCategory.getMissedModelElements()) {
                categoryError.errors().add(adaptError(categoryError.type(), missedModelElement, solution));
            }

            if (!categoryError.errors().isEmpty())
                categories.add(categoryError);
        }

        return categories;
    }

    private ModelError adaptError(String type, MissedModelElement element, ClassDiagram solution) {
        return ErrorAdapterFactory.getInstance().adaptError(type.toUpperCase(), element, solution);
    }

    public ErrorClassifier solution(ClassDiagram solution) {
        this.solution = solution;
        return this;
    }

 /*   public List<CategoryError> classify() {
        List<CategoryError> categories = new ArrayList<>();

        for (ModelElementCategory modelElementCategory : marksModel.getModelElementCategories()) {
            CategoryError categoryError = new CategoryError(modelElementCategory.getName().toLowerCase()
                    .replace(" ", "_"), new ArrayList<>());

            for (MissedModelElement missedModelElement : modelElementCategory.getMissedModelElements()) {
                categoryError.errors().add(adaptError(categoryError.type(), missedModelElement));
            }

            if (!categoryError.errors().isEmpty())
                categories.add(categoryError);
        }

        return categories;
    }

    private ModelError adaptError(String type, MissedModelElement element) {
        return ErrorAdapterFactory.getInstance().adaptError(type.toUpperCase(), element);
    }*/

}
