package me.loopbreak.hermesanalyzer.controllers;

import me.loopbreak.hermesanalyzer.entity.IntentInstanceEntity;
import me.loopbreak.hermesanalyzer.entity.IntentModelEntity;
import me.loopbreak.hermesanalyzer.objects.request.CreateInstanceRequest;
import me.loopbreak.hermesanalyzer.objects.request.CreateModelRequest;
import me.loopbreak.hermesanalyzer.services.IntentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/intent", produces = MediaType.APPLICATION_JSON_VALUE)
public class IntentController {

    private final IntentService intentService;

    @Autowired
    public IntentController(IntentService intentService) {
        this.intentService = intentService;
    }

    @PostMapping
    public IntentModelEntity createModel(@RequestBody CreateModelRequest request) {
        return intentService.createModel(request);
    }

    @GetMapping
    public List<IntentModelEntity> getModels() {
        return intentService.getModels();
    }

    @GetMapping(value = "/{intent}/instance")
    public List<IntentInstanceEntity> getInstances(@PathVariable String intent) {
        return intentService.getInstances(intent);
    }

    @PostMapping("/{intent}/instance")
    public IntentInstanceEntity createInstance(@PathVariable String intent,
                                               @RequestBody CreateInstanceRequest request) {
        return intentService.createInstance(intent, request);
    }
}
