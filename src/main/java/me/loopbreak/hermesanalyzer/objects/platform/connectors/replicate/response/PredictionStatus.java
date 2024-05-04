package me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.response;

public enum PredictionStatus {
    starting(true),
    processing(true),
    succeeded,
    failed,
    canceled;

    final boolean isRunning;

    PredictionStatus() {
        this.isRunning = false;
    }

    PredictionStatus(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public boolean isDone() {
        return !isRunning;
    }
}
