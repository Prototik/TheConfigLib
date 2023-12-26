package dev.tcl.config.api.autogen;

import dev.tcl.api.controller.DoubleSliderControllerBuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A regular option factory.
 * <p>
 * This creates a regular option with a
 * {@link DoubleSliderControllerBuilder} controller.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DoubleSlider {
    /**
     * The minimum value of the slider.
     * <p>
     * If the current value is at this minimum, if available,
     * the translation key {@code tcl.config.$configId.$fieldName.fmt.min}
     * will be used.
     */
    double min();

    /**
     * The maximum value of the slider.
     * <p>
     * If the current value is at this maximum, if available,
     * the translation key {@code tcl.config.$configId.$fieldName.fmt.max}
     * will be used.
     */
    double max();

    /**
     * The step size of this slider.
     * For example, if this is set to 0.1, the slider will
     * increment/decrement by 0.1 when dragging, no less, no more and
     * will always be a multiple of 0.1.
     */
    double step();

    /**
     * The format used to display the double.
     * This is the syntax used in {@link String#format(String, Object...)}.
     */
    String format() default "%.2f";
}
