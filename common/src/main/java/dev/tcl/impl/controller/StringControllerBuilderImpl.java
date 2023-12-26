package dev.tcl.impl.controller;

import dev.tcl.api.Controller;
import dev.tcl.api.Option;
import dev.tcl.api.controller.StringControllerBuilder;
import dev.tcl.gui.controllers.string.StringController;

public class StringControllerBuilderImpl extends AbstractControllerBuilderImpl<String> implements StringControllerBuilder {
    public StringControllerBuilderImpl(Option<String> option) {
        super(option);
    }

    @Override
    public Controller<String> build() {
        return new StringController(option);
    }
}
