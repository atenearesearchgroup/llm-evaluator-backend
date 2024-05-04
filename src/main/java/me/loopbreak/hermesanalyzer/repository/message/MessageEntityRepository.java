package me.loopbreak.hermesanalyzer.repository.message;

import me.loopbreak.hermesanalyzer.entity.messages.MessageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MessageEntityRepository<T extends MessageEntity> extends CrudRepository<T, Long> {

}