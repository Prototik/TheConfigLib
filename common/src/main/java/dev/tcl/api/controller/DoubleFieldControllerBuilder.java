package dev.tcl.api.controller;

import dev.tcl.api.Option;
import dev.tcl.impl.controller.DoubleFieldControllerBuilderImpl;

public interface DoubleFieldControllerBuilder extends NumberFieldControllerBuilder<Double, DoubleFieldControllerBuilder> {
    static DoubleFieldControllerBuilder create(Option<Double> option) {
        return new DoubleFieldControllerBuilderImpl(option);
    }
}
