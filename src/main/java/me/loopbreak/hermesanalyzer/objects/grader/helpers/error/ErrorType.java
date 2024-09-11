package me.loopbreak.hermesanalyzer.objects.grader.helpers.error;

import me.loopbreak.hermesanalyzer.objects.grader.helpers.error.impl.AssociationAdapter;
import me.loopbreak.hermesanalyzer.objects.grader.helpers.error.impl.AttributesAdapter;
import me.loopbreak.hermesanalyzer.objects.grader.helpers.error.impl.ClassAdapter;
import me.loopbreak.hermesanalyzer.objects.grader.helpers.error.impl.DummyAdapter;

public enum ErrorType {
    CLASSES(new ClassAdapter()),
    ATTRIBUTES(new AttributesAdapter()),
    ASSOCIATIONS(new AssociationAdapter()),
    DUMMY(new DummyAdapter());

    private final ErrorAdapter adapter;

    ErrorType(ErrorAdapter adapter) {
        this.adapter = adapter;
    }

    ErrorType() {
        this.adapter = null;
    }

    public ErrorAdapter getAdapter() {
        return adapter == null ? DUMMY.getAdapter() : adapter;
    }
}
