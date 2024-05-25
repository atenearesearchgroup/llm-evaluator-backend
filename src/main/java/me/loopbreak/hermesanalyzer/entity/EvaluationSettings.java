package me.loopbreak.hermesanalyzer.entity;

import jakarta.persistence.MappedSuperclass;
import org.jetbrains.annotations.NotNull;

@MappedSuperclass
public class EvaluationSettings {

    private Integer maxK;
    private Integer maxDrafts;
    private Integer maxRepeatingPrompt;

    public EvaluationSettings() {
    }

    public EvaluationSettings(int maxK, int maxDrafts, int maxRepeatingPrompt) {
        this.maxK = maxK;
        this.maxDrafts = maxDrafts;
        this.maxRepeatingPrompt = maxRepeatingPrompt;
    }

    public int getMaxK() {
        return maxK;
    }

    public void setMaxK(int maxK) {
        this.maxK = maxK;
    }

    public int getMaxDrafts() {
        return maxDrafts;
    }

    public void setMaxDrafts(int maxDrafts) {
        this.maxDrafts = maxDrafts;
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
        this.maxK = origin.maxK;
        this.maxDrafts = origin.maxDrafts;
        this.maxRepeatingPrompt = origin.maxRepeatingPrompt;
    }

}
