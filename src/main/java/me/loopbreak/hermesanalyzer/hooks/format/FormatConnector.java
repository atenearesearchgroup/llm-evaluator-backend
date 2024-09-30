package me.loopbreak.hermesanalyzer.hooks.format;

import me.loopbreak.hermesanalyzer.utils.PlantParser;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

public interface FormatConnector {

    default FormattedUml parse(@NotNull String response) {
        String plantUmlCode = response;

        plantUmlCode = PlantParser.getPlantUML(plantUmlCode);

        return transform(plantUmlCode);
    }

    FormattedUml transform(@NotNull String plantUmlCode);

    public record FormattedUml(InputStream transformed, String plantUmlCode) {

    }
}
