package me.loopbreak.hermesanalyzer.objects.grader;

import java.util.List;

public record EvaluationResult(double score, double maxScore, List<CategoryError> errors, String diagram) {

    public EvaluationResult withDiagram(String diagram) {
        return new EvaluationResult(score, maxScore, errors, diagram);
    }
}
