package dev.tcl.config.impl.autogen;

import dev.tcl.api.Option;
import dev.tcl.api.controller.ControllerBuilder;
import dev.tcl.api.controller.TickBoxControllerBuilder;
import dev.tcl.config.api.ConfigField;
import dev.tcl.config.api.autogen.SimpleOptionFactory;
import dev.tcl.config.api.autogen.OptionAccess;
import dev.tcl.config.api.autogen.TickBox;

public class TickBoxImpl extends SimpleOptionFactory<TickBox, Boolean> {
    @Override
    protected ControllerBuilder<Boolean> createController(TickBox annotation, ConfigField<Boolean> field, OptionAccess storage, Option<Boolean> option) {
        return TickBoxControllerBuilder.create(option);
    }
}
