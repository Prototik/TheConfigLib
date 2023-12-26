package dev.tcl.impl.controller;

import dev.tcl.api.Controller;
import dev.tcl.api.Option;
import dev.tcl.api.controller.TickBoxControllerBuilder;
import dev.tcl.gui.controllers.TickBoxController;

public class TickBoxControllerBuilderImpl extends AbstractControllerBuilderImpl<Boolean> implements TickBoxControllerBuilder {
    public TickBoxControllerBuilderImpl(Option<Boolean> option) {
        super(option);
    }

    @Override
    public Controller<Boolean> build() {
        return new TickBoxController(option);
    }
}
