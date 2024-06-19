package me.loopbreak.hermesanalyzer.controllers;

import me.loopbreak.hermesanalyzer.entity.ChatEntity;
import me.loopbreak.hermesanalyzer.entity.IntentInstanceEntity;
import me.loopbreak.hermesanalyzer.objects.request.CloneInstanceRequest;
import me.loopbreak.hermesanalyzer.objects.request.UpdateInstanceRequest;
import me.loopbreak.hermesanalyzer.repository.IntentInstanceRepository;
import me.loopbreak.hermesanalyzer.services.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/instance", produces = MediaType.APPLICATION_JSON_VALUE)
public class InstanceController {

    private final InstanceService instanceService;
    private final IntentInstanceRepository intentInstanceRepository;

    @Autowired
    public InstanceController(InstanceService instanceService,
                              IntentInstanceRepository intentInstanceRepository) {
        this.instanceService = instanceService;
        this.intentInstanceRepository = intentInstanceRepository;
    }

    @GetMapping("/{instance}")
    public IntentInstanceEntity getInstance(@PathVariable Long instance) {
        return instanceService.getInstance(instance);
    }

    @PutMapping("/{instance}")
    public IntentInstanceEntity updateInstance(@PathVariable Long instance, @RequestBody UpdateInstanceRequest request) {
        IntentInstanceEntity instanceEntity = instanceService.getInstance(instance);

        request.update(instanceEntity);

        return intentInstanceRepository.save(instanceEntity);
    }

    @DeleteMapping("/{instance}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteInstance(@PathVariable Long instance) {
        instanceService.deleteInstance(instance);
    }

    @PostMapping("/{instance}/clone")
    public Object cloneEntity(@PathVariable Long instance, @RequestBody CloneInstanceRequest request) {
        return instanceService.cloneInstance(instance, request);
    }

    @PostMapping("/{instance}/chats")
    public Object createChat(@PathVariable Long instance) {
        IntentInstanceEntity instanceEntity = instanceService.getInstance(instance);

        return instanceService.createChat(instanceEntity);
    }

    @GetMapping("/{instance}/chats")
    public List<ChatEntity> getChats(@PathVariable Long instance) {
        return instanceService.getInstance(instance).getChats();
    }

}
