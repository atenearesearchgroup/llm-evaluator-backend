package me.loopbreak.hermesanalyzer.controllers;

import me.loopbreak.hermesanalyzer.entity.messages.AIMessageEntity;
import me.loopbreak.hermesanalyzer.hooks.format.FormatConnector;
import me.loopbreak.hermesanalyzer.hooks.format.FormatConnectorImpl;
import me.loopbreak.hermesanalyzer.hooks.format.SyntaxException;
import me.loopbreak.hermesanalyzer.hooks.grader.EvaluatorConnector;
import me.loopbreak.hermesanalyzer.hooks.grader.EvaluatorConnectorImpl;
import me.loopbreak.hermesanalyzer.objects.grader.EvaluationResult;
import me.loopbreak.hermesanalyzer.objects.request.ScoreMessageRequest;
import me.loopbreak.hermesanalyzer.repository.message.AiMessageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static me.loopbreak.hermesanalyzer.services.ChatService.MESSAGE_INVALID_SYNTAX_SCORE;

@RestController
@CrossOrigin
@RequestMapping(value = "/message", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {


    private final AiMessageRepository aiMessageRepository;

    //        TODO: Add formatConnector Dependency Injection
    private FormatConnector formatConnector = new FormatConnectorImpl();
    //        TODO: Add evaluator Dependency Injection
    private EvaluatorConnector evaluator = new EvaluatorConnectorImpl();


    public MessageController(AiMessageRepository aiMessageRepository) {
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
    @Async
    public Future<EvaluationResult> evaluateMessage(@PathVariable Long messageId) {
        AIMessageEntity message = aiMessageRepository.findById(messageId).orElse(null);

        if (message == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found");

        String modelId = message.getPromptIteration().getChat().getIntentInstance().getIntentModel().getModelName();
        Path solutionFile;
        try {
            solutionFile = getSolutionPath(modelId);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while reading solution file");
        } catch (SyntaxException e) {
            e.printStackTrace();

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The given solution model syntax is not valid");
        }

        FormatConnector.FormattedUml parsedMessage;
        try {
            parsedMessage = formatConnector.parseTransform(message.getContent());
        } catch (SyntaxException e) {
            if (e.getErrors().isEmpty()) {
                e.getException().printStackTrace();
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "There was an error while changing the diagram format");
            }

            System.out.println("e.getErrors() = " + e.getErrors());

            /**
             * If the message is not a valid plantUML code, return {@link MESSAGE_INVALID_SYNTAX_SCORE} as score
             */
            EvaluationResult maxScore = evaluator.evaluate(null, solutionFile);
            return CompletableFuture.completedFuture(new EvaluationResult(MESSAGE_INVALID_SYNTAX_SCORE, maxScore.maxScore(), null, e.getErrors(), null));
        }

        return CompletableFuture.supplyAsync(() -> {
            EvaluationResult score = evaluator.evaluate(parsedMessage.transformed(), solutionFile);
            score = score.withDiagram(parsedMessage.plantUmlCode());

            return score;
        });
    }

    private Path getSolutionPath(String modelId) throws IOException, SyntaxException {
        Path solutionFile = FileController.UPLOADS_DIR.resolve(modelId).resolve(modelId + ".domain_model.cdm");

        if (Files.exists(solutionFile)) return solutionFile;

        Path pumlFile = FileController.UPLOADS_DIR.resolve(modelId).resolve(modelId + ".puml");

        if (!Files.exists(pumlFile)) return solutionFile;

        InputStream content = formatConnector.transform(Files.readString(pumlFile))
                .transformed();
        Files.write(solutionFile, content.readAllBytes());

        return solutionFile;
    }
}
