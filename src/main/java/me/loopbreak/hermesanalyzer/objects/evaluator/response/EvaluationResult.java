package me.loopbreak.hermesanalyzer.objects.evaluator.response;

import java.util.List;

public record EvaluationResult(double score, List<CategoryError> errors, String diagram) {

    public EvaluationResult withDiagram(String diagram) {
        return new EvaluationResult(score, errors, diagram);
    }
}
