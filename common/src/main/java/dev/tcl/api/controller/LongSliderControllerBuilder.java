package dev.tcl.api.controller;

import dev.tcl.api.Option;
import dev.tcl.impl.controller.LongSliderControllerBuilderImpl;

public interface LongSliderControllerBuilder extends SliderControllerBuilder<Long, LongSliderControllerBuilder> {
    static LongSliderControllerBuilder create(Option<Long> option) {
        return new LongSliderControllerBuilderImpl(option);
    }
}
