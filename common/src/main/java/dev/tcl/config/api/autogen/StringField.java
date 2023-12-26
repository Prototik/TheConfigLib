package dev.tcl.config.api.autogen;

import dev.tcl.api.controller.StringControllerBuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A regular option factory.
 * <p>
 * This creates a regular option with a
 * {@link StringControllerBuilder} controller.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface StringField {
}
