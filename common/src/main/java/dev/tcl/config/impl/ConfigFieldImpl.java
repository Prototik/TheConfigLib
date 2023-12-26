package dev.tcl.config.impl;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import dev.tcl.config.api.*;
import dev.tcl.config.api.autogen.AutoGen;
import dev.tcl.config.api.autogen.AutoGenField;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ConfigFieldImpl<T> implements ConfigField<T> {
    private FieldAccess<T> field;
    private final ReadOnlyFieldAccess<T> defaultField;
    private final ConfigClassHandler<?> parent;
    @Nullable
    private final SerialFieldImpl serial;
    @Nullable
    private final AutoGenField autoGen;

    private static final Converter<String, String> FIELD_NAME_CONVERTER = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE);

    public ConfigFieldImpl(FieldAccess<T> field, ReadOnlyFieldAccess<T> defaultField, ConfigClassHandler<?> parent) {
        this.field = field;
        this.defaultField = defaultField;
        this.parent = parent;

        this.serial = field.getAnnotation(SerialEntry.class).map(config -> new SerialFieldImpl(
                "".equals(config.value()) ? FIELD_NAME_CONVERTER.convert(field.name()) : config.value(),
                "".equals(config.comment()) ? Optional.empty() : Optional.of(config.comment()),
                config.required(),
                config.nullable()
        )).orElse(null);
        this.autoGen = field.getAnnotation(AutoGen.class).map(autoGen -> new AutoGenFieldImpl(
                autoGen.category(),
                "".equals(autoGen.group()) ? Optional.empty() : Optional.of(autoGen.group())
        )).orElse(null);
    }

    @Override
    public FieldAccess<T> access() {
        return field;
    }

    @ApiStatus.Internal
    void trackNewInstance(Object newInstance) {
        field = field.forInstance(newInstance);
    }

    @Override
    public ReadOnlyFieldAccess<T> defaultAccess() {
        return defaultField;
    }

    @Override
    public ConfigClassHandler<?> parent() {
        return parent;
    }

    @Override
    public Optional<SerialField> serial() {
        return Optional.ofNullable(this.serial);
    }

    @Override
    public Optional<AutoGenField> autoGen() {
        return Optional.ofNullable(this.autoGen);
    }

    private record SerialFieldImpl(String serialName, Optional<String> comment, boolean required,
                                   boolean nullable) implements SerialField {
    }

    private record AutoGenFieldImpl(String category, Optional<String> group) implements AutoGenField {
    }
}
