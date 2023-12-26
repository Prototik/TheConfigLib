package dev.tcl.api.controller;

import dev.tcl.api.Option;
import dev.tcl.impl.controller.StringControllerBuilderImpl;

public interface StringControllerBuilder extends ControllerBuilder<String> {
    static StringControllerBuilder create(Option<String> option) {
        return new StringControllerBuilderImpl(option);
    }
}
