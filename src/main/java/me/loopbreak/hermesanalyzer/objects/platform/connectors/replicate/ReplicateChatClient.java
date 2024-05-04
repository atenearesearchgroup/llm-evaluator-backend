package me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate;

import me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.response.PredictionUrls;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;

import java.util.List;
import java.util.StringJoiner;

public class ReplicateChatClient implements ChatClient {

    private ReplicateApi replicateApi;
    private ReplicateOptions replicateOptions;

    public ReplicateChatClient(ReplicateApi replicateApi, ReplicateOptions replicateOptions) {
        this.replicateApi = replicateApi;
        this.replicateOptions = replicateOptions;
    }

    @Override
    public ChatResponse call(Prompt prompt) {
        String dump = generateChat(prompt);
        PredictionUrls predictionUrls = replicateOptions.official() ?
                replicateApi.createOfficialPrediction(dump, replicateOptions.modelName(), replicateOptions.modelOwner()) :
                replicateApi.createPrediction(dump, replicateOptions.version());

        String response = replicateApi.generateResponse(predictionUrls);

        Generation generation = new Generation(response);

        return new ChatResponse(List.of(generation));
    }

    private String generateChat(Prompt prompt) {
        StringJoiner stringJoiner = new StringJoiner("\n");

        for (Message instruction : prompt.getInstructions()) {
            stringJoiner.add(instruction.getContent());
        }

        return stringJoiner.toString();
    }
}
