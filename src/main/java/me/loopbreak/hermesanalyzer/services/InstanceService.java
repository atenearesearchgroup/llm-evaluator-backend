package me.loopbreak.hermesanalyzer.services;

import me.loopbreak.hermesanalyzer.entity.DraftEntity;
import me.loopbreak.hermesanalyzer.entity.IntentInstanceEntity;
import me.loopbreak.hermesanalyzer.objects.request.CloneInstanceRequest;
import me.loopbreak.hermesanalyzer.repository.DraftEntityRepository;
import me.loopbreak.hermesanalyzer.repository.IntentInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InstanceService {

    private final IntentInstanceRepository intentInstanceRepository;
    private final DraftEntityRepository draftEntityRepository;

    @Autowired
    public InstanceService(IntentInstanceRepository intentInstanceRepository, DraftEntityRepository draftEntityRepository) {
        this.intentInstanceRepository = intentInstanceRepository;
        this.draftEntityRepository = draftEntityRepository;
    }

    public IntentInstanceEntity getInstance(Long instance) {
        IntentInstanceEntity instanceEntity = intentInstanceRepository.findById(instance).orElse(null);

        if (instanceEntity == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Instance not found");

//        System.out.println(instanceEntity.getModelSettings().get);

        return instanceEntity;
    }

    @Transactional
    public void deleteInstance(Long instance) {
        IntentInstanceEntity instanceEntity = intentInstanceRepository.findById(instance).orElse(null);

        if (instanceEntity == null)
            throw new ResponseStatusException(HttpStatus.FOUND, "Instance not found");

        intentInstanceRepository.delete(instanceEntity);
    }

    @Transactional
    public IntentInstanceEntity cloneInstance(Long instance, CloneInstanceRequest request) {
        IntentInstanceEntity instanceEntity = getInstance(instance);

        IntentInstanceEntity newInstance = instanceEntity.clone();

        request.apply(newInstance);

        intentInstanceRepository.save(newInstance);

        return newInstance;
    }

    /**
     * TODO: Add tests
     *
     * @param instanceEntity
     * @return
     */
    @Transactional
    public DraftEntity createDraft(IntentInstanceEntity instanceEntity) {
        int iterations = instanceEntity.getDrafts().size();

        if (instanceEntity.getDrafts().stream().anyMatch(draft -> !draft.isFinalized())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is a draft still not finalized");
        }

        if (instanceEntity.getMaxDrafts() <= iterations) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Max drafts reached");
        }

        DraftEntity draft = new DraftEntity(instanceEntity, iterations);

        draftEntityRepository.save(draft);

        instanceEntity.getDrafts().add(draft);

        return draft;
    }


}
