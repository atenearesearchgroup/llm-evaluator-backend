package me.loopbreak.hermesanalyzer.objects.evaluator;

import me.loopbreak.hermesanalyzer.objects.evaluator.dto.CompensationMark;
import me.loopbreak.hermesanalyzer.objects.evaluator.dto.MarksModel;
import me.loopbreak.hermesanalyzer.objects.evaluator.dto.MissedModelElement;
import me.loopbreak.hermesanalyzer.objects.evaluator.dto.ModelElementCategory;

import java.util.Map;
import java.util.stream.Collectors;

public class MarksCalculator {

    private MarksModel marksModel;

    private MarksCalculator(MarksModel marksModel) {
        this.marksModel = marksModel;
    }

    public static MarksCalculator create(MarksModel marksModel) {
        return new MarksCalculator(marksModel);
    }

    public double calculateMarks() {
        // Create a map from MarkValue _id to MarkValue points
        Map<String, Double> markValueMap = marksModel.getMarksMap().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getValue().getId(),
                        entry -> entry.getValue().getPointsSum()
                ));

        double totalSum = 0.0;

        // Iterate through each ModelElementCategory
        for (ModelElementCategory category : marksModel.getModelElementCategories()) {
            // Sum the points for each mark id in the category's marks list
            for (String markId : category.getMarks()) {
                if (markValueMap.containsKey(markId)) {
                    totalSum += markValueMap.get(markId);
                }
            }
            for (MissedModelElement missedModelElement : category.getMissedModelElements()) {
                CompensationMark compensationMark = missedModelElement.getCompensationMark();

                if (compensationMark == null) continue;

                totalSum += compensationMark.getPoints();
            }
        }

        return totalSum;
    }
}
