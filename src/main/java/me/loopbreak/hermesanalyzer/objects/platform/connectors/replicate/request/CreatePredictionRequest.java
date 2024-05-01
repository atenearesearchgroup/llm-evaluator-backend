package me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreatePredictionRequest(String version, InputRequest inputRequest) {

    public CreatePredictionRequest(@JsonProperty("version") String version, @JsonProperty("input") InputRequest inputRequest) {
        this.version = version;
        this.inputRequest = inputRequest;
    }

    @JsonProperty("version")
    public String version() {
        return version;
    }

    @JsonProperty("input")
    public InputRequest inputRequest() {
        return inputRequest;
    }

}
