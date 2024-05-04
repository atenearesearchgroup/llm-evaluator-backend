package me.loopbreak.hermesanalyzer.repository;

import me.loopbreak.hermesanalyzer.entity.messages.PromptIterationEntity;
import org.springframework.data.repository.CrudRepository;

public interface PromptIterationEntityRepository extends CrudRepository<PromptIterationEntity, Long> {
}