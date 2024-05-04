package me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record InputRequest(String prompt) {

    public InputRequest(@JsonProperty("prompt") String prompt) {
        this.prompt = prompt;
    }

    @JsonProperty("prompt")
    public String prompt() {
        return prompt;
    }

}
