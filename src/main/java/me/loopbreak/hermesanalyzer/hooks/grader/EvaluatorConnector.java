package me.loopbreak.hermesanalyzer.hooks.grader;

import me.loopbreak.hermesanalyzer.objects.grader.EvaluationResult;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.nio.file.Path;

public interface EvaluatorConnector {

    EvaluationResult evaluate(@Nullable InputStream text, Path solutionFile);
}
