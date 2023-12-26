package dev.tcl.config.api;

import org.jetbrains.annotations.ApiStatus;

/**
 * A writable field instance access.
 *
 * @param <T> the type of the field
 */
public interface FieldAccess<T> extends ReadOnlyFieldAccess<T> {
    /**
     * Sets the value of the field.
     * @param value the value to set
     */
    void set(T value);

    @Override
    @ApiStatus.Internal
    FieldAccess<T> forInstance(Object instance);
}
