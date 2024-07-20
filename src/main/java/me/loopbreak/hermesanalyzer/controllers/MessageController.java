package me.loopbreak.hermesanalyzer.controllers;

import me.loopbreak.hermesanalyzer.entity.messages.AIMessageEntity;
import me.loopbreak.hermesanalyzer.objects.evaluator.EvaluationResult;
import me.loopbreak.hermesanalyzer.objects.evaluator.EvaluatorConnector;
import me.loopbreak.hermesanalyzer.objects.evaluator.FormatConnector;
import me.loopbreak.hermesanalyzer.objects.request.ScoreMessageRequest;
import me.loopbreak.hermesanalyzer.repository.message.AiMessageRepository;
import me.loopbreak.hermesanalyzer.services.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.InputStream;

@RestController
@CrossOrigin
@RequestMapping(value = "/message", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {


    private final ChatService chatService;
    private final AiMessageRepository aiMessageRepository;

    public MessageController(ChatService chatService, AiMessageRepository aiMessageRepository) {
        this.chatService = chatService;
        this.aiMessageRepository = aiMessageRepository;
    }

    @PostMapping("/{messageId}/score")
    public AIMessageEntity scoreMessage(@PathVariable Long messageId,
                                        @RequestBody ScoreMessageRequest request) {
        AIMessageEntity message = aiMessageRepository.findById(messageId).orElse(null);

        if (message == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found");

        message.setScore(request.score());

        message = aiMessageRepository.save(message);

        return message;
    }

    @GetMapping("/{messageId}/evaluate")
    public EvaluationResult evaluateMessage(@PathVariable Long messageId) {
        AIMessageEntity message = aiMessageRepository.findById(messageId).orElse(null);

        if (message == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found");

//        TODO: Add formatConnector Dependency Injection
        FormatConnector formatConnector = null;

        InputStream parsedMessage = formatConnector.parse(message.getContent());

//        TODO: Add evaluator Dependency Injection
        EvaluatorConnector evaluator = null;

        EvaluationResult score = evaluator.evaluate(parsedMessage);


        return score;
    }
}
