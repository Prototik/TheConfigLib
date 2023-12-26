package dev.tcl.api.controller;

import dev.tcl.api.Option;
import dev.tcl.impl.controller.FloatFieldControllerBuilderImpl;

public interface FloatFieldControllerBuilder extends NumberFieldControllerBuilder<Float, FloatFieldControllerBuilder> {
    static FloatFieldControllerBuilder create(Option<Float> option) {
        return new FloatFieldControllerBuilderImpl(option);
    }
}
