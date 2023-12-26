package dev.tcl.config.impl;

import dev.tcl.config.api.FieldAccess;

import java.lang.annotation.Annotation;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public record VarHandleFieldAccess<T>(
        VarHandle handle, Field field, Object instance
) implements FieldAccess<T> {
    @Override
    public void set(T value) {
        handle.set(instance, value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get() {
        return (T) handle.get(instance);
    }

    @Override
    public String name() {
        return field.getName();
    }

    @Override
    public Type type() {
        return field.getGenericType();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<T> typeClass() {
        return (Class<T>) field.getType();
    }

    @Override
    public <A extends Annotation> Optional<A> getAnnotation(Class<A> annotationClass) {
        return Optional.ofNullable(field.getAnnotation(annotationClass));
    }

    @Override
    public List<? extends Annotation> getAnnotations() {
        return Arrays.asList(field.getAnnotations());
    }

    @Override
    public VarHandleFieldAccess<T> forInstance(Object instance) {
        return new VarHandleFieldAccess<>(handle, field, instance);
    }
}
