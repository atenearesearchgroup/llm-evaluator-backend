package me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record InputRequest(String text) {

    public InputRequest(@JsonProperty("text") String text) {
        this.text = text;
    }

    @JsonProperty("text")
    public String text() {
        return text;
    }

}
