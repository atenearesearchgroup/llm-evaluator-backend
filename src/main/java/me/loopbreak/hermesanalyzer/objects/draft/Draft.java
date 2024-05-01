package me.loopbreak.hermesanalyzer.objects.draft;

import me.loopbreak.hermesanalyzer.objects.draft.messages.Message;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class Draft implements Iterable<PromptPhase> {
    private int currentIteration;
    private String systemPrompt;
    private String id;
    private List<PromptPhase> history;

    public Draft(int currentIteration) {
        this(currentIteration, UUID.randomUUID().toString(), null, new ArrayList<>());
    }

    public Draft(int currentIteration, String id, String systemPrompt, List<PromptPhase> history) {
        this.currentIteration = currentIteration;
        this.id = id;
        this.systemPrompt = systemPrompt;
        this.history = history;
    }

    public int getCurrentIteration() {
        return currentIteration;
    }

    public String getId() {
        return id;
    }

    public List<PromptPhase> getHistory() {
        return history;
    }

    public void setSystemPrompt(@Nullable String systemPrompt) {
        this.systemPrompt = systemPrompt;
    }

    public String getSystemPrompt() {
        return systemPrompt;
    }

    public List<Message> getMessages() {
        List<Message> messages = new ArrayList<>();
        for (PromptPhase promptPhase : this) {
            for (PromptIteration iteration : promptPhase) {
                messages.addAll(iteration.getMessages());
            }
        }
        messages.sort(Message::compareTo);
        return messages;
    }

    @Override
    public @NotNull Iterator<PromptPhase> iterator() {
        return history.iterator();
    }
}
