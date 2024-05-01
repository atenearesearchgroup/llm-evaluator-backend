package me.loopbreak.hermesanalyzer.objects.platform;

import me.loopbreak.hermesanalyzer.objects.models.Model;
import me.loopbreak.hermesanalyzer.objects.models.ModelSettings;
import me.loopbreak.hermesanalyzer.objects.models.ModelSettingsLike;

import java.util.List;

public interface Platform {

    Model getModel(ModelSettings modelSettings);

    default Model getModel(ModelSettingsLike holder) {
        return getModel(holder.asModelSettings());
    }

    List<String> getAvailableModels();
}
