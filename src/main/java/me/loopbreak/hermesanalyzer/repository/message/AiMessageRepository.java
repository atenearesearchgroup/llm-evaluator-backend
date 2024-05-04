package me.loopbreak.hermesanalyzer.repository.message;

import jakarta.transaction.Transactional;
import me.loopbreak.hermesanalyzer.entity.messages.AIMessageEntity;

@Transactional
public interface AiMessageRepository extends MessageEntityRepository<AIMessageEntity> {
}
