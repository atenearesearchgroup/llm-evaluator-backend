package me.loopbreak.hermesanalyzer.hooks.grader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import me.loopbreak.hermesanalyzer.objects.grader.CategoryError;
import me.loopbreak.hermesanalyzer.objects.grader.EvaluationResult;
import me.loopbreak.hermesanalyzer.objects.grader.dto.MarksModel;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.List;

public class DummyConnectorImpl implements EvaluatorConnector {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final EvaluationResult DEFAULT_RESULT = new EvaluationResult(0.0, 0.0, List.of(), null, null);

    @Override
    public EvaluationResult evaluate(InputStream text, Path solutionFile) {
        MarksModel model = getSampleModel();

        if (model == null) {
            System.out.println("Model is null");
            return DEFAULT_RESULT;
        }

//        double score = model.getCalculator().calculateMarks();
        double score = 0.0;

//        List<CategoryError> errors = model.getErrorClassifier().classify();
        List<CategoryError> errors = List.of();

        EvaluationResult result = new EvaluationResult(score, model.getMaxPoints(), errors, null, null);

        return result;
    }

    private MarksModel sampleModel = null;

    @Nullable
    private MarksModel getSampleModel() {

        if (sampleModel != null) {
            return sampleModel;
        }

        InputStream inputStream = DummyConnectorImpl.class.getResourceAsStream("/evaluation_sample.json");

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
