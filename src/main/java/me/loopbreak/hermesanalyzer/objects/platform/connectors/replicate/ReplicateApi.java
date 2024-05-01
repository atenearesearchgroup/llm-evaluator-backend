package me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate;

import me.loopbreak.hermesanalyzer.exceptions.ModelConnectionException;
import me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.request.CreateOfficialPredictionRequest;
import me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.request.CreatePredictionRequest;
import me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.request.InputRequest;
import me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.response.CreatePredictionResponse;
import me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.response.GetPredictionResponse;
import me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.response.PredictionStatus;
import me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.response.PredictionUrls;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class ReplicateApi {

    private static final Log logger = LogFactory.getLog(ReplicateApi.class);
    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final ResponseErrorHandler responseErrorHandler;
    private RestClient restClient;

    public ReplicateApi(String token) {
        Consumer<HttpHeaders> defaultHeaders = (headers) -> {
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(token);
        };

        this.restClient = RestClient.builder()
                .baseUrl("https://api.replicate.com/v1")
                .defaultHeaders(defaultHeaders).build();

        this.responseErrorHandler = new ReplicateApi.ReplicateResponseErrorHandler();
    }

    public PredictionUrls createOfficialPrediction(String prompt, String modelName, String modelOwner) {
//        models/{model_owner}/{model_name}/predictions
        CreateOfficialPredictionRequest predictionRequest = new CreateOfficialPredictionRequest(new InputRequest(prompt));

        CreatePredictionResponse response = this.restClient.post().uri("/models/{model_owner}/{model_name}/predictions"
                        .replace("{model_owner}", modelOwner).replace("{model_name}", modelName))
                .body(predictionRequest)
                .retrieve()
                .onStatus(this.responseErrorHandler)
                .body(CreatePredictionResponse.class);

        if (response == null)
            throw new ModelConnectionException("Could not connect to the Replicate model provider to create official prediction");

        if (response.error() != null) {
            throw new ModelConnectionException(response.error());
        }

        if (response.status().isDone()) {
            throw new ModelConnectionException(String.format("Model is not running, there is some error (status: %s)", response.status()));
        }

        return response.urls();
    }

    public PredictionUrls createPrediction(String prompt, String version) {
        CreatePredictionRequest predictionRequest = new CreatePredictionRequest(version, new InputRequest(prompt));

        CreatePredictionResponse response = this.restClient.post().uri("/predictions")
                .body(predictionRequest)
                .retrieve()
                .onStatus(this.responseErrorHandler)
                .body(CreatePredictionResponse.class);

        if (response == null)
            throw new ModelConnectionException("Could not connect to the Replicate model provider to create prediction");

        if (response.error() != null) {
            throw new ModelConnectionException(response.error());
        }

        if (response.status().isDone()) {
            throw new ModelConnectionException(String.format("Model is not running, there is some error (status: %s)", response.status()));
        }

        return response.urls();
    }

    public String generateResponse(PredictionUrls urls) {
        CompletableFuture<String> resultFuture = new CompletableFuture<>();

        final ScheduledFuture<?> scheduledFuture = executor.scheduleAtFixedRate(() -> {
            try {
                String result = pollPrediction(urls);

                if (result != null) {
                    resultFuture.complete(result);
                }

            } catch (Exception exception) {
                resultFuture.completeExceptionally(exception);
            }

        }, 0, 1, TimeUnit.SECONDS);

        resultFuture.whenComplete((result, throwable) -> scheduledFuture.cancel(true));

        return resultFuture.join();
    }

    private String pollPrediction(PredictionUrls urls) {
        GetPredictionResponse response = this.restClient.get().uri(urls.get())
                .retrieve()
                .onStatus(this.responseErrorHandler)
                .body(GetPredictionResponse.class);

        if (response == null)
            throw new ModelConnectionException("Could not connect to the Replicate model provider to get the prediction");

        if (response.error() != null) {
            throw new ModelConnectionException(response.error());
        }

        if (response.status() != PredictionStatus.SUCCEEDED) {
            throw new ModelConnectionException(String.format("Model failed, there is some error (status: %s)", response.status()));
        }

        return response.output();
    }

    private static class ReplicateResponseErrorHandler implements ResponseErrorHandler {
        private ReplicateResponseErrorHandler() {
        }

        public boolean hasError(ClientHttpResponse response) throws IOException {
            return response.getStatusCode().isError();
        }

        public void handleError(ClientHttpResponse response) throws IOException {
            if (response.getStatusCode().isError()) {
                int statusCode = response.getStatusCode().value();
                String statusText = response.getStatusText();
                String message = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
                ReplicateApi.logger.warn(String.format("[%s] %s - %s", statusCode, statusText, message));
                throw new RuntimeException(String.format("[%s] %s - %s", statusCode, statusText, message));
            }
        }
    }

}
