package dev.tcl.impl.controller;

import dev.tcl.api.Controller;
import dev.tcl.api.Option;
import dev.tcl.api.controller.FloatSliderControllerBuilder;
import dev.tcl.api.controller.ValueFormatter;
import dev.tcl.gui.controllers.slider.FloatSliderController;
import org.jetbrains.annotations.NotNull;

public class FloatSliderControllerBuilderImpl extends AbstractControllerBuilderImpl<Float> implements FloatSliderControllerBuilder {
    private float min, max;
    private float step;
    private ValueFormatter<Float> formatter = FloatSliderController.DEFAULT_FORMATTER;

    public FloatSliderControllerBuilderImpl(Option<Float> option) {
        super(option);
    }

    @Override
    public FloatSliderControllerBuilder range(Float min, Float max) {
        this.min = min;
        this.max = max;
        return this;
    }

    @Override
    public FloatSliderControllerBuilder step(Float step) {
        this.step = step;
        return this;
    }

    @Override
    public FloatSliderControllerBuilder formatValue(@NotNull ValueFormatter<Float> formatter) {
        this.formatter = formatter;
        return this;
    }

    @Override
    public Controller<Float> build() {
        return new FloatSliderController(option, min, max, step, formatter);
    }
}
