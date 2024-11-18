package me.loopbreak.hermesanalyzer.objects.grader.helpers;

import ca.mcgill.sel.classdiagram.Association;
import ca.mcgill.sel.classdiagram.AssociationEnd;
import ca.mcgill.sel.classdiagram.ClassDiagram;
import ca.mcgill.sel.grading.marks.*;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;


public class MarksCalculator {

    private final MarksModel marksModel;
    private ClassDiagram solutionModel = null;
    private final Map<String, PriorityQueue<Double>> commentMap = new HashMap<>();
    private final Map<String, Integer> duplicatedElements = new HashMap<>();

    private MarksCalculator(MarksModel marksModel) {
        this.marksModel = marksModel;
    }

    public static MarksCalculator of(MarksModel marksModel) {
        return new MarksCalculator(marksModel);
    }

    public MarksCalculator withSolution(ClassDiagram marksModel) {
        this.solutionModel = marksModel;
        duplicatedNumber();
        return this;
    }

    private void duplicatedNumber() {
        duplicatedElements.clear();
        if (solutionModel == null) return;

        for (Association association : solutionModel.getAssociations()) {
            for (AssociationEnd end : association.getEnds()) {
                String comment = "Equivalent to solution association end " + end.getClassifier().getName() + "." + end.getName() + ".";
                duplicatedElements.merge(comment, 1, Integer::sum);
            }
        }

    }

    public double calculateMarks() {
        if (solutionModel == null) {
            throw new RuntimeException("Solution model is not set");
        }

        double totalSum = 0.0;

        // Iterate through each ModelElementCategory
        for (ModelElementCategory category : marksModel.getModelElementCategories()) {
            // Sum the points for each mark id in the category's marks list
            for (Mark markId : category.getMarks()) {
                String comment = adjustComment(markId);
                PriorityQueue<Double> oldPoints = commentMap.computeIfAbsent(comment, k -> new PriorityQueue<>());
                double points = markId.getPoints();

                if (oldPoints.size() < duplicatedElements.getOrDefault(comment, 1)) {
                    oldPoints.add(points);
                    totalSum += points;
                    continue;
                }

                double oldPointsSum = oldPoints.peek();

                if (oldPointsSum >= points)
                    continue;

                oldPoints.poll();
                oldPoints.add(points);

                totalSum += points - oldPointsSum;
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

    private String adjustComment(Mark mark) {
        if (!(mark.eContainer() instanceof FeatureMark featureMark)) return mark.getComment();

        String comment = mark.getComment();
        if (!comment.equalsIgnoreCase("Full marks")) return comment;

        return "Full marks " + featureMark.getReferencedObjectName();
    }
}

