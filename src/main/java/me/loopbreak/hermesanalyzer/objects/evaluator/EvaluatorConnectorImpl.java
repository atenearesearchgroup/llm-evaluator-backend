package me.loopbreak.hermesanalyzer.objects.evaluator;

import me.loopbreak.hermesanalyzer.objects.evaluator.response.EvaluationResult;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EvaluatorConnectorImpl implements EvaluatorConnector {

    private static final EvaluationResult DEFAULT_RESULT = new EvaluationResult(0.0, new ArrayList<>());
    private static final String URL = "https://api.huggingface.co";

//    private final RestClient client = getClient();

    //    TODO: Implement evaluation logic
    @Override
    public EvaluationResult evaluate(InputStream text) {

        return DEFAULT_RESULT;
    }

    private RestClient getClient() {
        Consumer<HttpHeaders> defaultHeaders = (headers) -> {
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
//            headers.setBearerAuth(token);
        };

        return RestClient.builder()
                .baseUrl(URL)
                .defaultHeaders(defaultHeaders).build();
    }
}
