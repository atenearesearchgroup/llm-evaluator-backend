package me.loopbreak.hermesanalyzer;

import me.loopbreak.hermesanalyzer.configuration.ReplicateProviderService;
import me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.ReplicateConnectionProperties;
import org.junit.jupiter.api.Test;
import org.springframework.ai.autoconfigure.vertexai.gemini.VertexAiGeminiAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@ContextConfiguration(locations = "classpath:application.properties")
@SpringBootTest
class HermesApplicationTests {

    @Autowired
    ReplicateProviderService replicateProviderService;

    @Test
    void contextLoads() {
        ReplicateConnectionProperties properties = replicateProviderService.getOptions();
        System.out.println(properties.getToken());
    }

}
