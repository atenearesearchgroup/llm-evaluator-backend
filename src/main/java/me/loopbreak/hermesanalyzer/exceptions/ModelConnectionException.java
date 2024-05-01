package me.loopbreak.hermesanalyzer.exceptions;

public class ModelConnectionException extends RuntimeException {

    public ModelConnectionException(Throwable throwable) {
        super(throwable);
    }

    public ModelConnectionException(String message) {
        super(message);
    }
}
