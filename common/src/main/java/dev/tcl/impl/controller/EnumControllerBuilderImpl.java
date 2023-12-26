package dev.tcl.impl.controller;

import dev.tcl.api.Controller;
import dev.tcl.api.Option;
import dev.tcl.api.controller.EnumControllerBuilder;
import dev.tcl.api.controller.ValueFormatter;
import dev.tcl.gui.controllers.cycling.EnumController;
import org.jetbrains.annotations.NotNull;

public class EnumControllerBuilderImpl<T extends Enum<T>> extends AbstractControllerBuilderImpl<T> implements EnumControllerBuilder<T> {
    private Class<T> enumClass;
    private ValueFormatter<T> formatter = EnumController.getDefaultFormatter();

    public EnumControllerBuilderImpl(Option<T> option) {
        super(option);
    }

    @Override
    public EnumControllerBuilder<T> enumClass(Class<T> enumClass) {
        this.enumClass = enumClass;
        return this;
    }

    @Override
    public EnumControllerBuilder<T> formatValue(@NotNull ValueFormatter<T> formatter) {
        this.formatter = formatter;
        return this;
    }

    @Override
    public Controller<T> build() {
        return new EnumController<>(option, formatter, enumClass.getEnumConstants());
    }
}
