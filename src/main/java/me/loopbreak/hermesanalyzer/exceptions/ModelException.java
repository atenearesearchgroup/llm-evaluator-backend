package me.loopbreak.hermesanalyzer.exceptions;

import me.loopbreak.hermesanalyzer.objects.models.Model;

public class ModelException extends RuntimeException {

    private final Model model;

    public ModelException(Model model, String message) {
        super(message);
        this.model = model;
    }

    public ModelException(Model model, Throwable cause) {
        super(cause);
        this.model = model;
    }

    public Model getModel() {
        return model;
    }
}
