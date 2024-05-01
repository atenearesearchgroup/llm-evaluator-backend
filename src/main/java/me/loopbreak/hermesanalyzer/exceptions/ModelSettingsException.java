package me.loopbreak.hermesanalyzer.exceptions;

import me.loopbreak.hermesanalyzer.objects.models.ModelSettings;
import me.loopbreak.hermesanalyzer.objects.models.ModelSettingsLike;

public class ModelSettingsException extends RuntimeException {

    private final ModelSettingsLike modelSettings;

    public ModelSettingsException(ModelSettingsLike modelSettings, String message) {
        super(message);
        this.modelSettings = modelSettings;
    }

    public ModelSettings getModelSettings() {
        return modelSettings.asModelSettings();
    }
}
