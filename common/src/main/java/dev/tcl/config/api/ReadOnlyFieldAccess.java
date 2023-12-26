package dev.tcl.config.api;

import dev.tcl.config.impl.ReadOnlyFieldAccessDelegate;
import org.jetbrains.annotations.ApiStatus;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

/**
 * An abstract interface for accessing properties of an instance of a field.
 * You do not need to worry about exceptions as the implementation
 * will handle them.
 *
 * @param <T> the type of the field
 */
public interface ReadOnlyFieldAccess<T> {
    /**
     * @return the current value of the field.
     */
    T get();

    /**
     * @return the name of the field.
     */
    String name();

    /**
     * @return the type of the field.
     */
    Type type();

    /**
     * @return the class of the field.
     */
    Class<T> typeClass();

    <A extends Annotation> Optional<A> getAnnotation(Class<A> annotationClass);

    List<? extends Annotation> getAnnotations();

    @ApiStatus.Internal
    ReadOnlyFieldAccess<T> forInstance(Object o);

    default ReadOnlyFieldAccess<T> asReadOnly() {
        return new ReadOnlyFieldAccessDelegate<>(this);
    }

    @ApiStatus.Internal
    default Optional<T> newInstance() {
        try {
            return Optional.of(typeClass().getDeclaredConstructor().newInstance());
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            return Optional.empty();
        }
    }
}
