package me.loopbreak.hermesanalyzer.objects.draft;

public class EvaluationSettings {
    private int maxK;
    private int maxDrafts;
    private int maxSamePrompt;

    public EvaluationSettings(int maxK, int maxDrafts, int maxSamePrompt) {
        this.maxK = maxK;
        this.maxDrafts = maxDrafts;
        this.maxSamePrompt = maxSamePrompt;
    }

    public int getMaxK() {
        return maxK;
    }

    public int getMaxDrafts() {
        return maxDrafts;
    }

    public int getMaxSamePrompt() {
        return maxSamePrompt;
    }
}
