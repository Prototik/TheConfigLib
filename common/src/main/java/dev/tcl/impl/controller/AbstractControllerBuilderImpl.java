package dev.tcl.impl.controller;

import dev.tcl.api.Option;
import dev.tcl.api.controller.ControllerBuilder;

public abstract class AbstractControllerBuilderImpl<T> implements ControllerBuilder<T> {
    protected final Option<T> option;

    protected AbstractControllerBuilderImpl(Option<T> option) {
        this.option = option;
    }
}
