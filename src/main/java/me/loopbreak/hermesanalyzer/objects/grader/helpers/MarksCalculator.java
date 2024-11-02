package me.loopbreak.hermesanalyzer.objects.grader.helpers;

import ca.mcgill.sel.grading.marks.Mark;
import ca.mcgill.sel.grading.marks.MarksModel;
import ca.mcgill.sel.grading.marks.MissedModelElement;
import ca.mcgill.sel.grading.marks.ModelElementCategory;

import java.util.HashMap;
import java.util.Map;


public class MarksCalculator {

    private final MarksModel marksModel;
    private final Map<String, Double> commentMap = new HashMap<>();

    private MarksCalculator(MarksModel marksModel) {
        this.marksModel = marksModel;
    }

    public static MarksCalculator of(MarksModel marksModel) {
        return new MarksCalculator(marksModel);
    }

    public double calculateMarks() {
        // Create a map from MarkValue _id to MarkValue points
//        Map<String, Double> markValueMap = marksModel.getMarksMap().stream()
//                .collect(Collectors.toMap(
//                        entry -> entry.getValue().getId(),
//                        entry -> entry.getValue().getPointsSum()
//                ));

        double totalSum = 0.0;

        // Iterate through each ModelElementCategory
        for (ModelElementCategory category : marksModel.getModelElementCategories()) {
            // Sum the points for each mark id in the category's marks list
            for (Mark markId : category.getMarks()) {
//                if (markValueMap.containsKey(markId)) {
//                    totalSum += markValueMap.get(markId);
//                }
                double oldPoints = commentMap.getOrDefault(markId.getComment(), 0.0);

                if (oldPoints >= markId.getPoints()) continue;

                commentMap.put(markId.getComment(), markId.getPoints());

                totalSum += markId.getPoints() - oldPoints;
            }
            for (MissedModelElement missedModelElement : category.getMissedModelElements()) {
                Mark compensationMark = missedModelElement.getCompensationMark();

                if (compensationMark == null) continue;

                totalSum += compensationMark.getPoints();
            }
            commentMap.clear();
        }

        return totalSum;
    }


    /*public double calculateMarks() {
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
    }*/

}

