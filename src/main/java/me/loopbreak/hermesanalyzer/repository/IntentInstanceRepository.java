package me.loopbreak.hermesanalyzer.repository;

import me.loopbreak.hermesanalyzer.entity.IntentInstanceEntity;
import me.loopbreak.hermesanalyzer.entity.IntentModelEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface IntentInstanceRepository extends ListCrudRepository<IntentInstanceEntity, Long> {
    List<IntentInstanceEntity> findByIntentModel(IntentModelEntity intentModel);

    List<IntentInstanceEntity> findByDisplayNameContainsIgnoreCase(String displayName);
}