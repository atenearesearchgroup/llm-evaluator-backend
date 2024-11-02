package me.loopbreak.hermesanalyzer.objects.evaluator;

import me.loopbreak.hermesanalyzer.hooks.format.FormatConnectorImpl;
import me.loopbreak.hermesanalyzer.hooks.format.SyntaxException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
class FormatConnectorImplTest {

    private FormatConnectorImpl formatConnector = new FormatConnectorImpl();

    @Test
    void transform() throws SyntaxException {
        String plantUml = """
                @startuml
                Bob -> Alice : hello
                @enduml
                """;

        InputStream inputStream = formatConnector.transform(plantUml).transformed();

        assertThat(inputStream).isNotNull().isNotEmpty();
    }
}