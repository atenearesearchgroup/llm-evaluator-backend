package me.loopbreak.hermesanalyzer.controllers;

import me.loopbreak.hermesanalyzer.entity.messages.AIMessageEntity;
import me.loopbreak.hermesanalyzer.objects.evaluator.DummyConnectorImpl;
import me.loopbreak.hermesanalyzer.objects.evaluator.EvaluatorConnector;
import me.loopbreak.hermesanalyzer.objects.evaluator.FormatConnector;
import me.loopbreak.hermesanalyzer.objects.evaluator.FormatConnectorImpl;
import me.loopbreak.hermesanalyzer.objects.evaluator.response.EvaluationResult;
import me.loopbreak.hermesanalyzer.objects.request.ScoreMessageRequest;
import me.loopbreak.hermesanalyzer.repository.message.AiMessageRepository;
import me.loopbreak.hermesanalyzer.services.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        FormatConnector formatConnector = new FormatConnectorImpl();
//        FormatConnector formatConnector = new DummyFormatConnector();

//        System.out.println("message.getContent() = " + message.getContent());

//        InputStream parsedMessage = formatConnector.parse(message.getContent());

//        TODO: Add evaluator Dependency Injection
        EvaluatorConnector evaluator = new DummyConnectorImpl();

//        EvaluationResult score = evaluator.evaluate(parsedMessage);
        EvaluationResult score = evaluator.evaluate(null);
//        score.withDiagram(message.toMessage().getOutputDiagram());

//        message.setScore(score.score());
//        aiMessageRepository.save(message);

        return score;
    }
}
