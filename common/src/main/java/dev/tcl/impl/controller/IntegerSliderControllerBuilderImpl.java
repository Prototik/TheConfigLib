package dev.tcl.impl.controller;

import dev.tcl.api.Controller;
import dev.tcl.api.Option;
import dev.tcl.api.controller.IntegerSliderControllerBuilder;
import dev.tcl.api.controller.ValueFormatter;
import dev.tcl.gui.controllers.slider.IntegerSliderController;
import org.jetbrains.annotations.NotNull;

public class IntegerSliderControllerBuilderImpl extends AbstractControllerBuilderImpl<Integer> implements IntegerSliderControllerBuilder {
    private int min, max;
    private int step;
    private ValueFormatter<Integer> formatter = IntegerSliderController.DEFAULT_FORMATTER;

    public IntegerSliderControllerBuilderImpl(Option<Integer> option) {
        super(option);
    }

    @Override
    public IntegerSliderControllerBuilder range(Integer min, Integer max) {
        this.min = min;
        this.max = max;
        return this;
    }

    @Override
    public IntegerSliderControllerBuilder step(Integer step) {
        this.step = step;
        return this;
    }

    @Override
    public IntegerSliderControllerBuilder formatValue(@NotNull ValueFormatter<Integer> formatter) {
        this.formatter = formatter;
        return this;
    }

    @Override
    public Controller<Integer> build() {
        return new IntegerSliderController(option, min, max, step, formatter);
    }
}
