package me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreateOfficialPredictionRequest(InputRequest inputRequest) {

    public CreateOfficialPredictionRequest(@JsonProperty("input") InputRequest inputRequest) {
        this.inputRequest = inputRequest;
    }

    @JsonProperty("input")
    public InputRequest inputRequest() {
        return inputRequest;
    }

}

