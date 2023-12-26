package dev.tcl.api.controller;

import dev.tcl.api.Option;
import dev.tcl.impl.controller.DoubleSliderControllerBuilderImpl;

public interface DoubleSliderControllerBuilder extends SliderControllerBuilder<Double, DoubleSliderControllerBuilder> {
    static DoubleSliderControllerBuilder create(Option<Double> option) {
        return new DoubleSliderControllerBuilderImpl(option);
    }
}
