package me.loopbreak.hermesanalyzer.repository;

import me.loopbreak.hermesanalyzer.entity.IntentInstanceEntity;
import me.loopbreak.hermesanalyzer.entity.IntentModelEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IntentInstanceRepository extends CrudRepository<IntentInstanceEntity, Long> {
    List<IntentInstanceEntity> findByIntentModel(IntentModelEntity intentModel);
}