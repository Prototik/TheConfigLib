package dev.tcl.api.controller;

import dev.tcl.api.Option;
import dev.tcl.impl.controller.TickBoxControllerBuilderImpl;

public interface TickBoxControllerBuilder extends ControllerBuilder<Boolean> {
    static TickBoxControllerBuilder create(Option<Boolean> option) {
        return new TickBoxControllerBuilderImpl(option);
    }
}
