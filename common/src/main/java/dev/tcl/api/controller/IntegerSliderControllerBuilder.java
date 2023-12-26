package dev.tcl.api.controller;

import dev.tcl.api.Option;
import dev.tcl.impl.controller.IntegerSliderControllerBuilderImpl;

public interface IntegerSliderControllerBuilder extends SliderControllerBuilder<Integer, IntegerSliderControllerBuilder> {
    static IntegerSliderControllerBuilder create(Option<Integer> option) {
        return new IntegerSliderControllerBuilderImpl(option);
    }
}
