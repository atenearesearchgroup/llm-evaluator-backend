package me.loopbreak.hermesanalyzer.objects.models;

import me.loopbreak.hermesanalyzer.objects.draft.Draft;
import me.loopbreak.hermesanalyzer.objects.draft.messages.AIMessage;

import java.util.concurrent.CompletableFuture;

public interface Model {

    ModelSettings getModelSettings();

    CompletableFuture<AIMessage> send(Draft draft);

}
