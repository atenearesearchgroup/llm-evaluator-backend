package me.loopbreak.hermesanalyzer.objects.draft;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PromptPhase implements Iterable<PromptIteration> {

    private String id;
    private List<PromptIteration> iterations;

    public PromptPhase(String id) {
        this(id, new ArrayList<>());
    }

    public PromptPhase(String id, List<PromptIteration> iterations) {
        this.id = id;
        this.iterations = iterations;
    }

    public String getId() {
        return id;
    }

    public List<PromptIteration> getIterations() {
        return iterations;
    }


    @Override
    public @NotNull Iterator<PromptIteration> iterator() {
        return iterations.iterator();
    }
}
