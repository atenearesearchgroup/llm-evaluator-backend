package me.loopbreak.hermesanalyzer.objects.evaluator;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class DummyFormatConnector implements FormatConnector {

    public InputStream transform(@NotNull String plantUmlCode) {
        return new ByteArrayInputStream(plantUmlCode.getBytes());
    }
}
