package me.loopbreak.hermesanalyzer.objects.metrics;

public interface MetricsMergeStrategy {

        Metrics merge(Metrics... metrics);
}
