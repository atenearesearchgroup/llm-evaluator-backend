package me.loopbreak.hermesanalyzer.repository;

import me.loopbreak.hermesanalyzer.entity.DraftEntity;
import me.loopbreak.hermesanalyzer.entity.messages.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface DraftEntityRepository extends CrudRepository<DraftEntity, Long> {
}