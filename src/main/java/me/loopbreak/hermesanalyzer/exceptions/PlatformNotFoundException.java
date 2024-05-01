package me.loopbreak.hermesanalyzer.exceptions;

public class PlatformNotFoundException extends RuntimeException {

    public PlatformNotFoundException(String identifier) {
        super("Platform with identifier " + identifier + " not found");
    }
}
