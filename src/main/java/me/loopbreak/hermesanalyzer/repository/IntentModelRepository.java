package me.loopbreak.hermesanalyzer.repository;

import me.loopbreak.hermesanalyzer.entity.IntentModelEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface IntentModelRepository extends ListCrudRepository<IntentModelEntity, String> {
    IntentModelEntity findByDisplayNameLikeIgnoreCase(String displayName);
}