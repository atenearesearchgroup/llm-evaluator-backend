package me.loopbreak.hermesanalyzer.objects.grader;

import java.util.List;

public record CategoryError(String type, List<ModelError> errors) {
}
