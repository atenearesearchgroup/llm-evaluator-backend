package me.loopbreak.hermesanalyzer.objects.evaluator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import me.loopbreak.hermesanalyzer.objects.evaluator.dto.MarksModel;
import me.loopbreak.hermesanalyzer.objects.evaluator.response.CategoryError;
import me.loopbreak.hermesanalyzer.objects.evaluator.response.EvaluationResult;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class DummyConnectorImpl implements EvaluatorConnector {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final EvaluationResult DEFAULT_RESULT = new EvaluationResult(0.0, List.of(), null);
    private static final String URL = "https://api.huggingface.co";

    @Override
    public EvaluationResult evaluate(InputStream text) {
        MarksModel model = getSampleModel();

        if (model == null) {
            System.out.println("Model is null");
            return DEFAULT_RESULT;
        }

        double score = model.getCalculator().calculateMarks();

        List<CategoryError> errors = model.getErrorClassifier().classify();


        EvaluationResult result = new EvaluationResult(score, errors, null);

        return result;
    }

    private MarksModel sampleModel = null;

    @Nullable
    private MarksModel getSampleModel() {

        if (sampleModel != null) {
            return sampleModel;
        }

        InputStream inputStream = EvaluatorSample.class.getResourceAsStream("/evaluation_sample.json");

        if (inputStream == null) {
            System.out.println("Input stream is null");
            return null;
        }

        JsonReader reader = new JsonReader(new InputStreamReader(inputStream));

        // Read the file and parse it to a JSON object
        sampleModel = gson.fromJson(reader, MarksModel.class);

        if (sampleModel == null) {
            System.out.println("Model is null");
            return null;
        }

        return sampleModel;
    }
}
