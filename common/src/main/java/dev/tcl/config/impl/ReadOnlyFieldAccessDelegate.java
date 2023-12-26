package dev.tcl.config.impl;

import dev.tcl.config.api.ReadOnlyFieldAccess;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public class ReadOnlyFieldAccessDelegate<T> implements ReadOnlyFieldAccess<T> {
    private final ReadOnlyFieldAccess<T> delegate;

    public ReadOnlyFieldAccessDelegate(ReadOnlyFieldAccess<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public T get() {
        return delegate.get();
    }

    @Override
    public String name() {
        return delegate.name();
    }

    @Override
    public Type type() {
        return delegate.type();
    }

    @Override
    public Class<T> typeClass() {
        return delegate.typeClass();
    }

    @Override
    public <A extends Annotation> Optional<A> getAnnotation(Class<A> annotationClass) {
        return delegate.getAnnotation(annotationClass);
    }

    @Override
    public List<? extends Annotation> getAnnotations() {
        return delegate.getAnnotations();
    }

    @Override
    public ReadOnlyFieldAccess<T> forInstance(Object o) {
        return new ReadOnlyFieldAccessDelegate<>(delegate.forInstance(o));
    }

    @Override
    public ReadOnlyFieldAccess<T> asReadOnly() {
        return this;
    }
}
