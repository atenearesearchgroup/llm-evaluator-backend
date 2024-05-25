package me.loopbreak.hermesanalyzer;

import me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.ReplicateConnectionProperties;
import me.loopbreak.hermesanalyzer.services.configuration.ReplicateProviderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

//@ContextConfiguration(locations = "classpath:application.properties")
@SpringBootTest
class HermesApplicationTests {

    @Autowired
    ReplicateProviderService replicateProviderService;

    @Test
    void contextLoads() {
        ReplicateConnectionProperties properties = replicateProviderService.getOptions();

        assertThat(properties.getToken())
                .isNotEmpty();
    }

}
