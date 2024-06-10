package me.loopbreak.hermesanalyzer.repository;

import me.loopbreak.hermesanalyzer.entity.ChatEntity;
import org.springframework.data.repository.CrudRepository;

public interface ChatEntityRepository extends CrudRepository<ChatEntity, Long> {
    ChatEntity findByIntentInstance_IdAndDraftNumber(Long id, int draftNumber);
}