package me.loopbreak.hermesanalyzer.objects.evaluator;

import java.io.InputStream;

public interface EvaluatorConnector {

    EvaluationResult evaluate(InputStream text);
}
