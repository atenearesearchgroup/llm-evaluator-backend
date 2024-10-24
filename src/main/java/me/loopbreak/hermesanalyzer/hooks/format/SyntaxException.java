package me.loopbreak.hermesanalyzer.hooks.format;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SyntaxException extends Exception {

    private List<String> errors;
    private Exception exception;

    public SyntaxException(@NotNull List<String> errors) {
        this.errors = errors;
        this.exception = null;
    }

    public SyntaxException(Exception exception) {
        this.exception = exception;
        this.errors = new ArrayList<>();
    }

    @Nullable
    public Exception getException() {
        return exception;
    }

    @NotNull
    public List<String> getErrors() {
        return errors;
    }

    @Override
    public String getMessage() {
        StringBuilder stringBuilder = new StringBuilder();

        if (getException() != null)
            stringBuilder.append(getException());

        stringBuilder.append(String.format("Errors (%1$s):", getErrors().size()));

        @NotNull List<String> strings = getErrors();
        for (int i = 0; i < strings.size(); i++) {
            String error = strings.get(i);
            stringBuilder.append('\n').append(String.format("(%1$d) ", i + 1)).append(error);
        }

        return stringBuilder.toString();
    }
}
