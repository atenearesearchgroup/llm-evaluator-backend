package me.loopbreak.hermesanalyzer.utils;

import me.loopbreak.hermesanalyzer.exceptions.InvalidModelOutputException;
import org.springframework.lang.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlantParser {

    //    "```plantuml TEXT ```"
    private static final String PLANTUML_REGEX = "```plantuml(.*?)\n```";
    private static final Pattern PLANTUML_PATTERN = Pattern.compile(PLANTUML_REGEX, Pattern.DOTALL);

    /**
     * Get the PlantUML from the content
     *
     * @param content
     * @return PlantUML diagram content
     * @throws InvalidModelOutputException If the PlantUML is not found in the content
     */
    public static String getPlantUML(String content) throws InvalidModelOutputException {
        String plantUML = getByMarkdownRegex(content);

        if (plantUML == null) {
            throw new InvalidModelOutputException("PlantUML not found in content");
        }

        return plantUML;
    }

    @Nullable
    private static String getByMarkdownRegex(String content) {
        Matcher matcher = PLANTUML_PATTERN.matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }


}
