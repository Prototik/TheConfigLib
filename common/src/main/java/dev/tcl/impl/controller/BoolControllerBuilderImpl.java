package dev.tcl.impl.controller;

import dev.tcl.api.Controller;
import dev.tcl.api.Option;
import dev.tcl.api.controller.BoolControllerBuilder;
import dev.tcl.api.controller.ValueFormatter;
import dev.tcl.gui.controllers.BooleanController;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

public class BoolControllerBuilderImpl extends AbstractControllerBuilderImpl<Boolean> implements BoolControllerBuilder {
    private boolean coloured = false;
    private ValueFormatter<Boolean> formatter = BooleanController.ON_OFF_FORMATTER;

    public BoolControllerBuilderImpl(Option<Boolean> option) {
        super(option);
    }

    @Override
    public BoolControllerBuilder coloured(boolean coloured) {
        this.coloured = coloured;
        return this;
    }

    @Override
    public BoolControllerBuilder formatValue(@NotNull ValueFormatter<Boolean> formatter) {
        Validate.notNull(formatter, "formatter cannot be null");

        this.formatter = formatter;
        return this;
    }

    @Override
    public BoolControllerBuilder onOffFormatter() {
        this.formatter = BooleanController.ON_OFF_FORMATTER;
        return this;
    }

    @Override
    public BoolControllerBuilder yesNoFormatter() {
        this.formatter = BooleanController.YES_NO_FORMATTER;
        return this;
    }

    @Override
    public BoolControllerBuilder trueFalseFormatter() {
        this.formatter = BooleanController.TRUE_FALSE_FORMATTER;
        return this;
    }

    @Override
    public Controller<Boolean> build() {
        return new BooleanController(option, formatter, coloured);
    }
}
