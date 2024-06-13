package me.loopbreak.hermesanalyzer.objects.platform.providers;

import me.loopbreak.hermesanalyzer.objects.models.Model;
import me.loopbreak.hermesanalyzer.objects.models.ModelSettings;
import me.loopbreak.hermesanalyzer.objects.platform.Platform;

import java.util.List;

public class DefaultProvider implements Platform {

    @Override
    public Model getModel(ModelSettings modelSettings) {
        throw new UnsupportedOperationException("Default provider does not support model creation");
    }

    @Override
    public List<String> getAvailableModels() {
        throw new UnsupportedOperationException("Default provider does not support model creation");
    }
}
