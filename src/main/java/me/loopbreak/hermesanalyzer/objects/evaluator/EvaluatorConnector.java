package me.loopbreak.hermesanalyzer.objects.evaluator;

import me.loopbreak.hermesanalyzer.objects.evaluator.response.EvaluationResult;

import java.io.InputStream;

public interface EvaluatorConnector {

    EvaluationResult evaluate(InputStream text);
}
