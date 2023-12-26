package dev.tcl.config.impl.autogen;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import dev.tcl.api.NameableEnum;
import dev.tcl.api.Option;
import dev.tcl.api.OptionDescription;
import dev.tcl.api.controller.ControllerBuilder;
import dev.tcl.config.api.autogen.CyclableEnum;
import dev.tcl.api.controller.ValueFormatter;
import dev.tcl.config.api.ConfigField;
import dev.tcl.config.api.ReadOnlyFieldAccess;
import dev.tcl.config.api.autogen.OptionAccess;
import dev.tcl.config.api.autogen.SimpleOptionFactory;
import net.minecraft.ChatFormatting;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.PlainTextContents;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class AbstractEnumFactory<A extends Annotation, E extends Enum<E>> extends SimpleOptionFactory<A, E> {
    public static final Converter<String, String> ENUM_CLASS_CONVERTER = CaseFormat.UPPER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE);

    protected ValueFormatter<E> createNameFormatter(ReadOnlyFieldAccess<E> fieldAccess) {
        return v -> {
            if (v instanceof NameableEnum n) {
                return n.getDisplayName();
            } else {
                return Component.translatable("tcl.config.enum.%s.%s".formatted(ENUM_CLASS_CONVERTER.convert(fieldAccess.typeClass().getSimpleName()), v.name().toLowerCase()));
            }
        };
    }

    protected ValueFormatter<E> createDescriptionFormatter(ReadOnlyFieldAccess<E> fieldAccess) {
        return v -> {
            if (v instanceof NameableEnum n) {
                return n.getDescription();
            } else {
                String key = "tcl.config.enum.%s.%s.desc".formatted(ENUM_CLASS_CONVERTER.convert(fieldAccess.typeClass().getSimpleName()), v.name().toLowerCase());
                if (Language.getInstance().has(key)) {
                    return Component.translatable(key);
                } else {
                    return Component.empty();
                }
            }
        };
    }

    @Override
    protected ControllerBuilder<E> createController(A annotation, ConfigField<E> field, OptionAccess storage, Option<E> option) {
        List<? extends E> values;

        var fieldType = field.access().typeClass();
        if (option.pendingValue() instanceof CyclableEnum<?> cyclableEnum) {
            values = Arrays.stream(cyclableEnum.allowedValues())
                    .flatMap(s -> {
                        if (fieldType.isInstance(s)) {
                            return Stream.of(fieldType.cast(s));
                        } else {
                            return Stream.empty();
                        }
                    }).collect(Collectors.toList());
        } else {
            E[] constants = fieldType.getEnumConstants();
            var allowedOrdinals = getAllowedOrdinals(annotation);
            values = IntStream.range(0, constants.length)
                    .filter(ordinal -> allowedOrdinals.length == 0 || Arrays.stream(allowedOrdinals).noneMatch(allowed -> allowed == ordinal))
                    .mapToObj(ordinal -> constants[ordinal])
                    .toList();
        }


        return createController(annotation, field, storage, option, values, createNameFormatter(field.defaultAccess()));
    }

    protected abstract int[] getAllowedOrdinals(A annotation);

    protected abstract ControllerBuilder<E> createController(A annotation, ConfigField<E> field, OptionAccess storage, Option<E> option, List<? extends E> values, ValueFormatter<E> valueFormatter);

    @Override
    protected OptionDescription.Builder description(E value, A annotation, ConfigField<E> field, OptionAccess storage) {
        ValueFormatter<E> nameFormatter = createNameFormatter(field.defaultAccess());
        ValueFormatter<E> descriptionFormatter = createDescriptionFormatter(field.defaultAccess());

        OptionDescription.Builder builder = super.description(value, annotation, field, storage);

        Component enumDescription = descriptionFormatter.format(value);
        if (enumDescription.getContents() != PlainTextContents.EMPTY || !enumDescription.getSiblings().isEmpty()) {
            builder.paragraph().text(Component.translatable("tcl.enum.desc",
                    nameFormatter.format(value).copy().withStyle(ChatFormatting.BOLD),
                    enumDescription));
        }

        return builder;
    }
}
