package me.loopbreak.hermesanalyzer.services;

import me.loopbreak.hermesanalyzer.entity.ChatEntity;
import me.loopbreak.hermesanalyzer.entity.IntentInstanceEntity;
import me.loopbreak.hermesanalyzer.objects.request.CloneInstanceRequest;
import me.loopbreak.hermesanalyzer.repository.ChatEntityRepository;
import me.loopbreak.hermesanalyzer.repository.IntentInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InstanceService {

    private final IntentInstanceRepository intentInstanceRepository;
    private final ChatEntityRepository chatEntityRepository;

    @Autowired
    public InstanceService(IntentInstanceRepository intentInstanceRepository, ChatEntityRepository chatEntityRepository) {
        this.intentInstanceRepository = intentInstanceRepository;
        this.chatEntityRepository = chatEntityRepository;
    }

    public IntentInstanceEntity getInstance(Long instance) {
        IntentInstanceEntity instanceEntity = intentInstanceRepository.findById(instance).orElse(null);

        if (instanceEntity == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Instance not found");

//        System.out.println(instanceEntity.getModelSettings().get);

        return instanceEntity;
    }

    @Transactional
    public void deleteInstance(Long instance) {
        IntentInstanceEntity instanceEntity = intentInstanceRepository.findById(instance).orElse(null);

        if (instanceEntity == null)
            throw new ResponseStatusException(HttpStatus.FOUND, "Instance not found");

        intentInstanceRepository.delete(instanceEntity);
    }

    @Transactional
    public IntentInstanceEntity cloneInstance(Long instance, CloneInstanceRequest request) {
        IntentInstanceEntity instanceEntity = getInstance(instance);

        IntentInstanceEntity newInstance = instanceEntity.clone();

        request.apply(newInstance);

        intentInstanceRepository.save(newInstance);

        return newInstance;
    }

    /**
     * TODO: Add tests
     *
     * @param instanceEntity
     * @return
     */
    @Transactional
    public ChatEntity createChat(IntentInstanceEntity instanceEntity) {
        int iterations = instanceEntity.getChats().size();

        if (instanceEntity.getChats().stream().anyMatch(chat -> !chat.isFinalized())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is a chat still not finalized");
        }

        if (instanceEntity.getMaxChats() <= iterations) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Max chats reached");
        }

        ChatEntity chat = new ChatEntity(instanceEntity, iterations);

        chatEntityRepository.save(chat);

        instanceEntity.getChats().add(chat);

        return chat;
    }


}
