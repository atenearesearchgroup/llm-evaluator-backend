package me.loopbreak.hermesanalyzer.entity;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class EvaluationSettings {

    private int maxK;
    private int maxDrafts;
    private int maxRepeatingPrompt;

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

    protected void copy(EvaluationSettings origin) {
        this.maxK = origin.maxK;
        this.maxDrafts = origin.maxDrafts;
        this.maxRepeatingPrompt = origin.maxRepeatingPrompt;
    }

}
