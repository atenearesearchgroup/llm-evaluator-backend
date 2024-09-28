package me.loopbreak.hermesanalyzer.objects.grader.helpers.error;

import me.loopbreak.hermesanalyzer.objects.grader.helpers.error.impl.*;

public enum ErrorType {
    CLASSES(new ClassAdapter()),
    DATA_TYPES(new DataTypeAdapter()),
    INHERITANCE(new InheritanceAdapter()),
    ATTRIBUTES(new AttributesAdapter()),
    ASSOCIATIONS(new AssociationAdapter()),
    ASSOCIATION_ENDS(new AssociationEndsAdapter()),
    ENUMERATION_TYPES(new EnumAdapter()),
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
