package me.loopbreak.hermesanalyzer.repository.message;

import jakarta.transaction.Transactional;
import me.loopbreak.hermesanalyzer.entity.messages.UserMessageEntity;

@Transactional
public interface UserMessageRepository extends MessageEntityRepository<UserMessageEntity> {
}
