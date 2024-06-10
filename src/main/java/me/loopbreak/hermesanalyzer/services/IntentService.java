package me.loopbreak.hermesanalyzer.services;

import me.loopbreak.hermesanalyzer.entity.IntentInstanceEntity;
import me.loopbreak.hermesanalyzer.entity.IntentModelEntity;
import me.loopbreak.hermesanalyzer.entity.ModelSettingsEntity;
import me.loopbreak.hermesanalyzer.objects.request.CreateInstanceRequest;
import me.loopbreak.hermesanalyzer.objects.request.CreateModelRequest;
import me.loopbreak.hermesanalyzer.repository.IntentInstanceRepository;
import me.loopbreak.hermesanalyzer.repository.IntentModelRepository;
import me.loopbreak.hermesanalyzer.repository.ModelSettingsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@Service
public class IntentService {

    private final IntentModelRepository intentModelRepository;
    private final IntentInstanceRepository intentInstanceRepository;
    private final ModelSettingsRepository modelSettingsRepository;

    public IntentService(IntentModelRepository intentModelRepository, IntentInstanceRepository intentInstanceRepository, ModelSettingsRepository modelSettingsRepository) {
        this.intentModelRepository = intentModelRepository;
        this.intentInstanceRepository = intentInstanceRepository;
        this.modelSettingsRepository = modelSettingsRepository;
    }

    public IntentModelEntity createModel(CreateModelRequest request) {
        IntentModelEntity model = intentModelRepository.findById(request.model()).orElse(null);
        if (model != null)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Model already exists");

        model = intentModelRepository.findByDisplayNameLikeIgnoreCase(request.displayName());

        if (model != null)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Model already exists");

        model = new IntentModelEntity(request.model(), request.displayName());

        model = intentModelRepository.save(model);

        return model;
    }

    public List<IntentInstanceEntity> getInstances(String intent) {
        IntentModelEntity model = intentModelRepository.findById(intent).orElse(null);

        if (model == null)
            return Collections.emptyList();
        return intentInstanceRepository.findByIntentModel(model);
    }

    @Transactional
    public IntentInstanceEntity createInstance(String intent, CreateInstanceRequest request) {
        IntentModelEntity model = intentModelRepository.findById(intent).orElse(null);

        if (model == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Intent model not found");

        ModelSettingsEntity modelSettingsEntity = new ModelSettingsEntity(request.modelSettings());

        IntentInstanceEntity instance = new IntentInstanceEntity(request.platform(), request.displayName(), model,
                request.evaluationSettings());

        instance = intentInstanceRepository.save(instance);

        modelSettingsEntity.setInstance(intentInstanceRepository.findById(instance.getId()).orElse(null));

        modelSettingsEntity = modelSettingsRepository.save(modelSettingsEntity);
        instance.setModelSettings(modelSettingsEntity);

        return instance;
    }

    public List<IntentModelEntity> getModels() {
        return intentModelRepository.findAll();
    }

    public IntentModelEntity getModel(String test) {
        return intentModelRepository.findById(test).orElse(null);
    }
}
