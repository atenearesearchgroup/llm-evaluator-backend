package me.loopbreak.hermesanalyzer.objects.platform.connectors.replicate.response;

public enum PredictionStatus {
    STARTING(true),
    PROCESSING(true),
    SUCCEEDED,
    FAILED,
    CANCELED;

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
