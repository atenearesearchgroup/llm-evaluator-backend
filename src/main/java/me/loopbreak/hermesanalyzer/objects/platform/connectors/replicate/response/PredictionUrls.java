package me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

//        "urls": {
//        "cancel": "https://api.replicate.com/v1/predictions/gm3qorzdhgbfurvjtvhg6dckhu/cancel",
//        "get": "https://api.replicate.com/v1/predictions/gm3qorzdhgbfurvjtvhg6dckhu"
//        }
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PredictionUrls(String cancel, String get) {

    public PredictionUrls(@JsonProperty("cancel") String cancel, @JsonProperty("get") String get) {
        this.cancel = cancel;
        this.get = get;
    }

    @JsonProperty("cancel")
    public String cancel() {
        return cancel;
    }

    @JsonProperty("get")
    public String get() {
        return get;
    }

}
