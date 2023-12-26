package dev.tcl.impl.controller;

import dev.tcl.api.Controller;
import dev.tcl.api.Option;
import dev.tcl.api.controller.FloatFieldControllerBuilder;
import dev.tcl.api.controller.ValueFormatter;
import dev.tcl.gui.controllers.slider.FloatSliderController;
import dev.tcl.gui.controllers.string.number.FloatFieldController;
import org.jetbrains.annotations.NotNull;

public class FloatFieldControllerBuilderImpl extends AbstractControllerBuilderImpl<Float> implements FloatFieldControllerBuilder {
    private float min = Float.MIN_VALUE;
    private float max = Float.MAX_VALUE;
    private ValueFormatter<Float> formatter = FloatSliderController.DEFAULT_FORMATTER;

    public FloatFieldControllerBuilderImpl(Option<Float> option) {
        super(option);
    }

    @Override
    public FloatFieldControllerBuilder min(Float min) {
        this.min = min;
        return this;
    }

    @Override
    public FloatFieldControllerBuilder max(Float max) {
        this.max = max;
        return this;
    }

    @Override
    public FloatFieldControllerBuilder range(Float min, Float max) {
        this.min = min;
        this.max = max;
        return this;
    }

    @Override
    public FloatFieldControllerBuilder formatValue(@NotNull ValueFormatter<Float> formatter) {
        this.formatter = formatter;
        return this;
    }

    @Override
    public Controller<Float> build() {
        return new FloatFieldController(option, min, max, formatter);
    }
}
