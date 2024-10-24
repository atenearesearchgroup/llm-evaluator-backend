package me.loopbreak.hermesanalyzer.objects.grader;

import java.util.List;

public record EvaluationResult(double score, double maxScore, List<CategoryError> errors, List<String> syntaxErrors,
                               String diagram) {

    public EvaluationResult withDiagram(String diagram) {
        return new EvaluationResult(score, maxScore, errors, syntaxErrors, diagram);
    }
}
