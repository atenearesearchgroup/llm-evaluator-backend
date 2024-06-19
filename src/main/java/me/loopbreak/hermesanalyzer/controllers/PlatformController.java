package me.loopbreak.hermesanalyzer.controllers;

import me.loopbreak.hermesanalyzer.objects.platform.DefaultPlatforms;
import me.loopbreak.hermesanalyzer.services.IntentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/platform", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlatformController {

    private final IntentService intentService;

    @Autowired
    public PlatformController(IntentService intentService) {
        this.intentService = intentService;
    }

    @GetMapping
    public List<String> getPlatforms() {
        return Arrays.stream(DefaultPlatforms.values()).map(Enum::name).map(String::toLowerCase).toList();
    }

}
