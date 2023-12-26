package dev.tcl.config.impl;

import dev.tcl.api.Binding;
import dev.tcl.config.api.FieldAccess;
import dev.tcl.config.api.ReadOnlyFieldAccess;

public record FieldBackedBinding<T>(FieldAccess<T> field, ReadOnlyFieldAccess<T> defaultField) implements Binding<T> {
    @Override
    public T getValue() {
        return field.get();
    }

    @Override
    public void setValue(T value) {
        field.set(value);
    }

    @Override
    public T defaultValue() {
        return defaultField.get();
    }
}
