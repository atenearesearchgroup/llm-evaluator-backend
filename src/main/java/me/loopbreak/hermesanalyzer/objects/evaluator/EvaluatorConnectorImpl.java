package me.loopbreak.hermesanalyzer.objects.evaluator;

import me.loopbreak.hermesanalyzer.objects.evaluator.response.EvaluationResult;

import java.io.InputStream;
import java.util.ArrayList;

public class EvaluatorConnectorImpl implements EvaluatorConnector {

    private static final EvaluationResult DEFAULT_RESULT = new EvaluationResult(0.0, new ArrayList<>(), null);
    private static final String URL = "https://api.huggingface.co";

//    private final RestClient client = getClient();

    //    TODO: Implement evaluation logic
    @Override
    public EvaluationResult evaluate(InputStream text) {

        return DEFAULT_RESULT;
    }

}
