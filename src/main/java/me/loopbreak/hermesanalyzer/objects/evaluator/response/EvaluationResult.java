package me.loopbreak.hermesanalyzer.objects.evaluator.response;

import java.util.List;

// TODO: Add feedback field...
public record EvaluationResult(double score, List<CategoryError> errors) {
}
