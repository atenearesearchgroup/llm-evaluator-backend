package me.loopbreak.hermesanalyzer.repository;

import me.loopbreak.hermesanalyzer.entity.DraftEntity;
import org.springframework.data.repository.CrudRepository;

public interface DraftEntityRepository extends CrudRepository<DraftEntity, Long> {
    DraftEntity findByIntentInstance_IdAndDraftNumber(Long id, int draftNumber);
}