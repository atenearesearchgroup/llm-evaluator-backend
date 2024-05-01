package me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.request.InputRequest;

/*
Example Response:
{
    "id": "gm3qorzdhgbfurvjtvhg6dckhu",
    "model": "replicate/hello-world",
    "version": "5c7d5dc6dd8bf75c1acaa8565735e7986bc5b66206b55cca93cb72c9bf15ccaa",
    "input": {
    "text": "Alice"
    },
    "logs": "",
    "error": null,
    "status": "starting",
    "created_at": "2023-09-08T16:19:34.765994657Z",
    "urls": {
        "cancel": "https://api.replicate.com/v1/predictions/gm3qorzdhgbfurvjtvhg6dckhu/cancel",
        "get": "https://api.replicate.com/v1/predictions/gm3qorzdhgbfurvjtvhg6dckhu"
    }
}
*/

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreatePredictionResponse(String id, String model, String version,
                                       InputRequest input, String logs, String error,
                                       PredictionStatus status, String created_at, PredictionUrls urls) {

    public CreatePredictionResponse(@JsonProperty("id") String id, @JsonProperty("model") String model,
                                    @JsonProperty("version") String version, @JsonProperty("input") InputRequest input,
                                    @JsonProperty("logs") String logs, @JsonProperty("error") String error,
                                    @JsonProperty("status") PredictionStatus status, @JsonProperty("created_at") String created_at,
                                    @JsonProperty("urls") PredictionUrls urls) {
        this.id = id;
        this.model = model;
        this.version = version;
        this.input = input;
        this.logs = logs;
        this.error = error;
        this.status = status;
        this.created_at = created_at;
        this.urls = urls;
    }

    @JsonProperty("id")
    public String id() {
        return id;
    }

    @JsonProperty("model")
    public String model() {
        return model;
    }

    @JsonProperty("version")
    public String version() {
        return version;
    }

    @JsonProperty("input")
    public InputRequest input() {
        return input;
    }

    @JsonProperty("logs")
    public String logs() {
        return logs;
    }

    @JsonProperty("error")
    public String error() {
        return error;
    }

    @JsonProperty("status")
    public PredictionStatus status() {
        return status;
    }

    @JsonProperty("created_at")
    public String created_at() {
        return created_at;
    }

    @JsonProperty("urls")
    public PredictionUrls urls() {
        return urls;
    }

}

