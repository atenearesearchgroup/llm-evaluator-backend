package me.loopbreak.hermesanalyzer.objects.evaluator.response;

import java.util.List;

public record CategoryError(String type, List<ModelError> errors) {
}
