package me.loopbreak.hermesanalyzer.objects.evaluator;

import me.loopbreak.hermesanalyzer.utils.PlantParser;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

public interface FormatConnector {

    default InputStream parse(@NotNull String response) {
        String plantUmlCode = response;

        plantUmlCode = PlantParser.getPlantUML(plantUmlCode);
//        todo: check if we need @startuml and @enduml

        return transform(plantUmlCode);
    }

    InputStream transform(@NotNull String plantUmlCode);
}
