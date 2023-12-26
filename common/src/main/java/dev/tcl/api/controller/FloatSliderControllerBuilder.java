package dev.tcl.api.controller;

import dev.tcl.api.Option;
import dev.tcl.impl.controller.FloatSliderControllerBuilderImpl;

public interface FloatSliderControllerBuilder extends SliderControllerBuilder<Float, FloatSliderControllerBuilder> {
    static FloatSliderControllerBuilder create(Option<Float> option) {
        return new FloatSliderControllerBuilderImpl(option);
    }
}
