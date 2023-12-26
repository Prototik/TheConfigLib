package dev.tcl.config.api.autogen;

import dev.tcl.api.controller.BoolControllerBuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An option factory.
 * <p>
 * This creates a regular option with a
 * {@link BoolControllerBuilder} controller.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Bool {
    enum Formatter {
        YES_NO,
        TRUE_FALSE,
        ON_OFF,
        /**
         * Uses the translation keys:
         * <ul>
         *     <li>true: {@code tcl.config.$configId.$fieldName.fmt.true}</li>
         *     <li>false: {@code tcl.config.$configId.$fieldName.fmt.false}</li>
         * </ul>
         */
        CUSTOM,
    }

    /**
     * The format used to display the boolean.
     */
    Formatter formatter() default Formatter.TRUE_FALSE;

    /**
     * Whether to color the formatted text green and red
     * depending on the value: true or false respectively.
     */
    boolean colored() default false;
}
