package dev.tcl.impl.controller;

import dev.tcl.api.Controller;
import dev.tcl.api.Option;
import dev.tcl.api.controller.IntegerFieldControllerBuilder;
import dev.tcl.api.controller.ValueFormatter;
import dev.tcl.gui.controllers.slider.IntegerSliderController;
import dev.tcl.gui.controllers.string.number.IntegerFieldController;
import org.jetbrains.annotations.NotNull;

public class IntegerFieldControllerBuilderImpl extends AbstractControllerBuilderImpl<Integer> implements IntegerFieldControllerBuilder {
    private int min = Integer.MIN_VALUE;
    private int max = Integer.MAX_VALUE;
    private ValueFormatter<Integer> formatter = IntegerSliderController.DEFAULT_FORMATTER;

    public IntegerFieldControllerBuilderImpl(Option<Integer> option) {
        super(option);
    }

    @Override
    public IntegerFieldControllerBuilder min(Integer min) {
        this.min = min;
        return this;
    }

    @Override
    public IntegerFieldControllerBuilder max(Integer max) {
        this.max = max;
        return this;
    }

    @Override
    public IntegerFieldControllerBuilder range(Integer min, Integer max) {
        this.min = min;
        this.max = max;
        return this;
    }

    @Override
    public IntegerFieldControllerBuilder formatValue(@NotNull ValueFormatter<Integer> formatter) {
        this.formatter = formatter;
        return this;
    }

    @Override
    public Controller<Integer> build() {
        return new IntegerFieldController(option, min, max, formatter);
    }
}
