package me.loopbreak.hermesanalyzer.entity;

import jakarta.persistence.MappedSuperclass;
import org.jetbrains.annotations.NotNull;

@MappedSuperclass
public class EvaluationSettings {

    private Integer maxErrors;
    private Integer maxChats;
    private Integer maxRepeatingPrompt;

    public EvaluationSettings() {
    }

    public EvaluationSettings(int maxErrors, int maxChats, int maxRepeatingPrompt) {
        this.maxErrors = maxErrors;
        this.maxChats = maxChats;
        this.maxRepeatingPrompt = maxRepeatingPrompt;
    }

    public int getMaxErrors() {
        return maxErrors;
    }

    public void setMaxErrors(int maxErrors) {
        this.maxErrors = maxErrors;
    }

    public int getMaxChats() {
        return maxChats;
    }

    public void setMaxChats(int maxChats) {
        this.maxChats = maxChats;
    }

    public int getMaxRepeatingPrompt() {
        return maxRepeatingPrompt;
    }

    public void setMaxRepeatingPrompt(int maxRepeatingPrompt) {
        this.maxRepeatingPrompt = maxRepeatingPrompt;
    }

    /**
     * TODO: Check if may be better to change back to protected scope
     *
     * @param origin
     */
    public void copy(@NotNull EvaluationSettings origin) {
        this.maxErrors = origin.maxErrors;
        this.maxChats = origin.maxChats;
        this.maxRepeatingPrompt = origin.maxRepeatingPrompt;
    }

}
