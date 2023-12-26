package dev.tcl.impl.controller;

import dev.tcl.api.Controller;
import dev.tcl.api.Option;
import dev.tcl.api.controller.DoubleFieldControllerBuilder;
import dev.tcl.api.controller.ValueFormatter;
import dev.tcl.gui.controllers.slider.DoubleSliderController;
import dev.tcl.gui.controllers.string.number.DoubleFieldController;
import org.jetbrains.annotations.NotNull;

public class DoubleFieldControllerBuilderImpl extends AbstractControllerBuilderImpl<Double> implements DoubleFieldControllerBuilder {
    private double min = Double.MIN_VALUE;
    private double max = Double.MAX_VALUE;
    private ValueFormatter<Double> formatter = DoubleSliderController.DEFAULT_FORMATTER;

    public DoubleFieldControllerBuilderImpl(Option<Double> option) {
        super(option);
    }

    @Override
    public DoubleFieldControllerBuilder min(Double min) {
        this.min = min;
        return this;
    }

    @Override
    public DoubleFieldControllerBuilder max(Double max) {
        this.max = max;
        return this;
    }

    @Override
    public DoubleFieldControllerBuilder range(Double min, Double max) {
        this.min = min;
        this.max = max;
        return this;
    }

    @Override
    public DoubleFieldControllerBuilder formatValue(@NotNull ValueFormatter<Double> formatter) {
        this.formatter = formatter;
        return this;
    }

    @Override
    public Controller<Double> build() {
        return new DoubleFieldController(option, min, max, formatter);
    }
}
