package me.loopbreak.hermesanalyzer.objects.platform.connectors.ollama;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import me.loopbreak.hermesanalyzer.exceptions.ModelConnectionException;
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
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class OllamaExtraConnector {

    private static OllamaExtraConnector INSTANCE = null;

    private static final Log logger = LogFactory.getLog(OllamaExtraConnector.class);
    private final ResponseErrorHandler responseErrorHandler;
    private RestClient restClient;

    public static OllamaExtraConnector getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OllamaExtraConnector();
        }
        return INSTANCE;
    }

    private OllamaExtraConnector() {
        Consumer<HttpHeaders> defaultHeaders = (headers) -> {
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        };
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:11434")
                .defaultHeaders(defaultHeaders).build();
        this.responseErrorHandler = new OllamaExtraConnector.OllamaResponseErrorHandler();
    }

    public List<String> getAvailableModels() {
        OllamaExtraConnector.LocalModelsResponse response = this.restClient.get().uri("/api/tags")
//            .body(chatRequest)
                .retrieve()
                .onStatus(this.responseErrorHandler)
                .body(OllamaExtraConnector.LocalModelsResponse.class);

        if (response == null)
            throw new ModelConnectionException("Could not connect to the Ollama model provider to get the available models");

        return response.models().stream().map(OllamaExtraConnector.OllamaModelInfo::name).toList();
    }

    private static class OllamaResponseErrorHandler implements ResponseErrorHandler {
        private OllamaResponseErrorHandler() {
        }

        public boolean hasError(ClientHttpResponse response) throws IOException {
            return response.getStatusCode().isError();
        }

        public void handleError(ClientHttpResponse response) throws IOException {
            if (response.getStatusCode().isError()) {
                int statusCode = response.getStatusCode().value();
                String statusText = response.getStatusText();
                String message = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
                OllamaExtraConnector.logger.warn(String.format("[%s] %s - %s", statusCode, statusText, message));
                throw new RuntimeException(String.format("[%s] %s - %s", statusCode, statusText, message));
            }
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static record LocalModelsResponse(List<OllamaExtraConnector.OllamaModelInfo> models) {
        public LocalModelsResponse(@JsonProperty("models") List<OllamaExtraConnector.OllamaModelInfo> models) {
            this.models = models;
        }

        @JsonProperty("models")
        public List<OllamaExtraConnector.OllamaModelInfo> models() {
            return this.models;
        }

    }

//    name=codellama:13b, model=codellama:13b, modified_at=2024-02-14T17:52:19.319695908+01:00, size=7365960935, digest=9f438cb9cd581fc025612d27f7c1a6669ff83a8bb0ed86c94fcf4c5440555697, details={parent_model=, format=gguf, family=llama, families=null, parameter_size=13B, quantization_level=Q4_0}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static record OllamaModelInfo(String name, String model, Instant modified_at, long size, String digest,
                                         Map<String, Object> details) {
        public OllamaModelInfo(@JsonProperty("name") String name, @JsonProperty("model") String model, @JsonProperty("modified_at") Instant modified_at, @JsonProperty("size") long size, @JsonProperty("digest") String digest, @JsonProperty("details") Map<String, Object> details) {
            this.name = name;
            this.model = model;
            this.modified_at = modified_at;
            this.size = size;
            this.digest = digest;
            this.details = details;
        }

        @JsonProperty("name")
        public String name() {
            return this.name;
        }

        @JsonProperty("model")
        public String model() {
            return this.model;
        }

        @JsonProperty("modified_at")
        public Instant modified_at() {
            return this.modified_at;
        }

        @JsonProperty("size")
        public long size() {
            return this.size;
        }

        @JsonProperty("digest")
        public String digest() {
            return this.digest;
        }

        @JsonProperty("details")
        public Map<String, Object> details() {
            return this.details;
        }

    }

}
