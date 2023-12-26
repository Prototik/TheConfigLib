package dev.tcl.config.api.autogen;

import dev.tcl.api.controller.ItemControllerBuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An option factory.
 * <p>
 * This creates a regular option with a
 * {@link ItemControllerBuilder} controller.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ItemField {
}
