package me.loopbreak.hermesanalyzer.objects.draft;

import me.loopbreak.hermesanalyzer.objects.draft.messages.AIMessage;
import me.loopbreak.hermesanalyzer.objects.draft.messages.Message;
import me.loopbreak.hermesanalyzer.objects.draft.messages.UserMessage;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PromptIteration implements Iterable<Message> {
    private int iteration;
    private int currentK;
    private List<Message> messages;

    public PromptIteration(int iteration) {
        this(iteration, 0, new ArrayList<>());
    }

    public PromptIteration(int iteration, int currentK, List<Message> messages) {
        this.iteration = iteration;
        this.currentK = currentK;
        this.messages = messages;
    }

    public int getIteration() {
        return iteration;
    }

    public int getCurrentK() {
        return currentK;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void addValues(UserMessage prompt, AIMessage response) {
        messages.add(prompt);
        messages.add(response);
    }

    /**
     * Even: UserMessage
     * Odd: AIMessage
     *
     * @return
     */
    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public @NotNull Iterator<Message> iterator() {
        return messages.iterator();
    }
}
