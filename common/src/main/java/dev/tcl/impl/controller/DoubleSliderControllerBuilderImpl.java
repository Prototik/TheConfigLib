package dev.tcl.impl.controller;

import dev.tcl.api.Controller;
import dev.tcl.api.Option;
import dev.tcl.api.controller.DoubleSliderControllerBuilder;
import dev.tcl.api.controller.ValueFormatter;
import dev.tcl.gui.controllers.slider.DoubleSliderController;
import org.jetbrains.annotations.NotNull;

public class DoubleSliderControllerBuilderImpl extends AbstractControllerBuilderImpl<Double> implements DoubleSliderControllerBuilder {
    private double min, max;
    private double step;
    private ValueFormatter<Double> formatter = DoubleSliderController.DEFAULT_FORMATTER;

    public DoubleSliderControllerBuilderImpl(Option<Double> option) {
        super(option);
    }

    @Override
    public DoubleSliderControllerBuilder range(Double min, Double max) {
        this.min = min;
        this.max = max;
        return this;
    }

    @Override
    public DoubleSliderControllerBuilder step(Double step) {
        this.step = step;
        return this;
    }

    @Override
    public DoubleSliderControllerBuilder formatValue(@NotNull ValueFormatter<Double> formatter) {
        this.formatter = formatter;
        return this;
    }

    @Override
    public Controller<Double> build() {
        return new DoubleSliderController(option, min, max, step, formatter);
    }
}
