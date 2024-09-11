package me.loopbreak.hermesanalyzer.hooks.grader;

import me.loopbreak.hermesanalyzer.objects.grader.EvaluationResult;

import java.io.InputStream;
import java.nio.file.Path;

public interface EvaluatorConnector {

    EvaluationResult evaluate(InputStream text, Path solutionFile);
}
