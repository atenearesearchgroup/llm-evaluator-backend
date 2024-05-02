package me.loopbreak.hermesanalyzer.objects.metrics;

public interface Metrics {

    int getScore();

    int getWeightedScore();

    int getSyntaxErrors();

    int neededPrompts();
}
