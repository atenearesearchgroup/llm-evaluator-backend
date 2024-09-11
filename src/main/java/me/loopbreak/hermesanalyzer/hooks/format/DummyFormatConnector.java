package me.loopbreak.hermesanalyzer.hooks.format;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;

public class DummyFormatConnector implements FormatConnector {

    public FormattedUml transform(@NotNull String plantUmlCode) {
        return new FormattedUml(new ByteArrayInputStream(plantUmlCode.getBytes()), plantUmlCode);
    }
}
