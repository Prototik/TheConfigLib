package dev.tcl.config.api.autogen;

import dev.tcl.api.controller.LongSliderControllerBuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A regular option factory.
 * <p>
 * This creates a regular option with a
 * {@link LongSliderControllerBuilder} controller.
 * <p>
 * If available, the translation key {@code tcl.config.$configId.$fieldName.fmt.$value}
 * is used where {@code $value} is the current value of the option, for example, {@code 5}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LongSlider {
    /**
     * The minimum value of the slider.
     */
    long min();

    /**
     * The maximum value of the slider.
     */
    long max();

    /**
     * The format used to display the integer.
     * This is the syntax used in {@link String#format(String, Object...)}.
     */
    long step();
}
