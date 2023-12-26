package dev.tcl.config.impl.autogen;

import dev.tcl.api.controller.ControllerBuilder;
import dev.tcl.api.controller.ValueFormattableController;
import dev.tcl.api.controller.ValueFormatter;
import dev.tcl.config.api.ReadOnlyFieldAccess;
import dev.tcl.config.api.autogen.CustomFormat;
import dev.tcl.config.api.autogen.FormatTranslation;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.ApiStatus;

import java.util.Optional;
import java.util.function.Supplier;

@ApiStatus.Internal
public final class AutoGenUtils {
    public static <T> void addCustomFormatterToController(ControllerBuilder<T> controller, ReadOnlyFieldAccess<T> field) {
        Optional<CustomFormat> formatter = field.getAnnotation(CustomFormat.class);
        Optional<FormatTranslation> translation = field.getAnnotation(FormatTranslation.class);

        if (formatter.isPresent() && translation.isPresent()) {
            throw new TCLAutoGenException("'%s': Cannot use both @CustomFormatter and @FormatTranslation on the same field.".formatted(field.name()));
        } else if (formatter.isEmpty() && translation.isEmpty()) {
            return;
        }

        if (!(controller instanceof ValueFormattableController<?, ?>)) {
            throw new TCLAutoGenException("Attempted to use @CustomFormatter or @FormatTranslation on an option factory for field '%s' that uses a controller that does not support this.".formatted(field.name()));
        }

        ValueFormattableController<T, ?> typedBuilder = (ValueFormattableController<T, ?>) controller;

        formatter.ifPresent(formatterClass -> {
            try {
                typedBuilder.formatValue((ValueFormatter<T>) formatterClass.value().getConstructor().newInstance());
            } catch (Exception e) {
                throw new TCLAutoGenException("'%s': Failed to instantiate formatter class %s.".formatted(field.name(), formatterClass.value().getName()), e);
            }
        });

        translation.ifPresent(annotation ->
                typedBuilder.formatValue(v -> Component.translatable(annotation.value(), v)));
    }

    public static <T> T constructNoArgsClass(Class<T> clazz, Supplier<String> constructorNotFoundConsumer, Supplier<String> constructorFailedConsumer) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            throw new TCLAutoGenException(constructorNotFoundConsumer.get(), e);
        } catch (Exception e) {
            throw new TCLAutoGenException(constructorFailedConsumer.get(), e);
        }
    }
}
