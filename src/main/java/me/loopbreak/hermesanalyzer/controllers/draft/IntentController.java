package me.loopbreak.hermesanalyzer.controllers.draft;

import me.loopbreak.hermesanalyzer.entity.IntentInstanceEntity;
import me.loopbreak.hermesanalyzer.entity.IntentModelEntity;
import me.loopbreak.hermesanalyzer.entity.ModelSettingsEntity;
import me.loopbreak.hermesanalyzer.objects.request.CreateInstanceRequest;
import me.loopbreak.hermesanalyzer.repository.IntentInstanceRepository;
import me.loopbreak.hermesanalyzer.repository.IntentModelRepository;
import me.loopbreak.hermesanalyzer.repository.ModelSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/intent", produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
public class IntentController {

    private final IntentModelRepository modelRepository;
    private final IntentInstanceRepository instanceRepository;
    private final ModelSettingsRepository modelSettingsRepository;

    @Autowired
    public IntentController(IntentInstanceRepository instanceRepository, IntentModelRepository modelRepository,
                            ModelSettingsRepository modelSettingsRepository) {
        this.instanceRepository = instanceRepository;
        this.modelRepository = modelRepository;
        this.modelSettingsRepository = modelSettingsRepository;
    }

    @GetMapping(value = "/{intent}")
    public List<IntentInstanceEntity> getInstances(@PathVariable String intent) {
        IntentModelEntity model = modelRepository.findById(intent).orElseThrow();
        return instanceRepository.findByIntentModel(model);
    }

    @GetMapping("/{intent}/instance/{id}")
    public IntentInstanceEntity getInstance(@PathVariable String intent, @PathVariable Long id) {
        return instanceRepository.findById(id).orElseThrow();
    }

    @PostMapping("/{intent}/instance")
    public IntentInstanceEntity createInstance(@PathVariable String intent,
                                               @RequestBody CreateInstanceRequest request) {
        IntentModelEntity model = modelRepository.findById(intent).orElseThrow();

        ModelSettingsEntity modelSettingsEntity = new ModelSettingsEntity(request.modelSettings());

        IntentInstanceEntity instance = new IntentInstanceEntity(request.platform(), model,
                request.evaluationSettings(), null);

        instance = instanceRepository.save(instance);

        modelSettingsEntity.setId(instance.getId());

        modelSettingsEntity = modelSettingsRepository.save(modelSettingsEntity);

        instance.setModelSettings(modelSettingsEntity);

        instance = instanceRepository.save(instance);

        return instance;
    }
}
