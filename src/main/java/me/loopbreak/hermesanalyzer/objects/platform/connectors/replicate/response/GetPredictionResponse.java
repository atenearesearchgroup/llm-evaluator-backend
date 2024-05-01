package me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record GetPredictionResponse(String output, String error, PredictionStatus status) {

    public GetPredictionResponse(@JsonProperty("output") String output,
                                 @JsonProperty("error") String error,
                                 @JsonProperty("status") PredictionStatus status) {
        this.output = output;
        this.error = error;
        this.status = status;
    }

    @JsonProperty("output")
    public String output() {
        return output;
    }

    @JsonProperty("error")
    public String error() {
        return error;
    }

    @JsonProperty("status")
    public PredictionStatus status() {
        return status;
    }
}
