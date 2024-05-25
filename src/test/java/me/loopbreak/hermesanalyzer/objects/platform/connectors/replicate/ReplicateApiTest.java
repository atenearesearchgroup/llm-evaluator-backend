package me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate;

import me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.response.PredictionUrls;
import me.loopbreak.hermesanalyzer.services.configuration.ReplicateProviderService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Disabled
class ReplicateApiTest {

    @Autowired
    private ReplicateProviderService replicateProviderService;

    @Test
    void pollPrediction() {
        ReplicateApi replicateApi = replicateProviderService.getApi();
        PredictionUrls predictionUrls = new PredictionUrls(null,
                "https://api.replicate.com/v1/predictions/h0476ae521rgp0cf8kza0ng1z8");

        String prediction = replicateApi.pollPrediction(predictionUrls);

        assertThat(prediction).isNotEmpty();
    }
}