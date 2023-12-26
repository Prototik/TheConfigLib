package dev.tcl.api.controller;

import dev.tcl.api.Option;
import dev.tcl.impl.controller.IntegerFieldControllerBuilderImpl;

public interface IntegerFieldControllerBuilder extends NumberFieldControllerBuilder<Integer, IntegerFieldControllerBuilder> {
    static IntegerFieldControllerBuilder create(Option<Integer> option) {
        return new IntegerFieldControllerBuilderImpl(option);
    }
}
