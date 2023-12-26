package dev.tcl.config.api.autogen;

import dev.tcl.api.NameableEnum;
import dev.tcl.api.controller.CyclingListControllerBuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An option factory.
 * <p>
 * This creates a regular option with a {@link CyclingListControllerBuilder}
 * controller. If the enum implements {@link CyclableEnum}, the allowed values will be used from that,
 * rather than every single enum constant in the class. If not, {@link EnumCycler#allowedOrdinals()} is used.
 * <p>
 * There are two methods of formatting for enum values. First, if the enum implements
 * {@link NameableEnum}, {@link NameableEnum#getDisplayName()} is used.
 * Otherwise, the translation key {@code tcl.config.enum.$enumClassName.$enumName} where
 * {@code $enumClassName} is the exact name of the class and {@code $enumName} is equal to the lower
 * case of {@link Enum#name()}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EnumCycler {
    /**
     * The allowed ordinals of the enum class. If empty, all ordinals are allowed.
     * This is only used if the enum does not implement {@link CyclableEnum}.
     */
    int[] allowedOrdinals() default {};
}
