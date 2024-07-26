package me.loopbreak.hermesanalyzer.objects.evaluator.helpers.error;

import me.loopbreak.hermesanalyzer.objects.evaluator.dto.MarksModel;
import me.loopbreak.hermesanalyzer.objects.evaluator.dto.MissedModelElement;
import me.loopbreak.hermesanalyzer.objects.evaluator.dto.ModelElementCategory;
import me.loopbreak.hermesanalyzer.objects.evaluator.response.CategoryError;
import me.loopbreak.hermesanalyzer.objects.evaluator.response.ModelError;

import java.util.ArrayList;
import java.util.List;

public class ErrorClassifier {

    private MarksModel marksModel;

    private ErrorClassifier(MarksModel marksModel) {
        this.marksModel = marksModel;
    }

    public static ErrorClassifier of(MarksModel marksModel) {
        return new ErrorClassifier(marksModel);
    }

    public List<CategoryError> classify() {
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
    }

}
