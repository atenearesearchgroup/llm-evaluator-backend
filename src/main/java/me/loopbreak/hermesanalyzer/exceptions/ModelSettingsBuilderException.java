package me.loopbreak.hermesanalyzer.exceptions;

import me.loopbreak.hermesanalyzer.objects.models.ModelSettingsBuilder;

public class ModelSettingsBuilderException extends RuntimeException {

    private final ModelSettingsBuilder modelSettingsBuilder;

    public ModelSettingsBuilderException(ModelSettingsBuilder modelSettingsBuilder, String message) {
        super(message);
        this.modelSettingsBuilder = modelSettingsBuilder;
    }

    public ModelSettingsBuilder getModelSettingsBuilder() {
        return modelSettingsBuilder;
    }
}
