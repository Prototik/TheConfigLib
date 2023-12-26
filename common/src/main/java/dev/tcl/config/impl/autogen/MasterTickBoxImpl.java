package dev.tcl.config.impl.autogen;

import dev.tcl.api.Option;
import dev.tcl.api.controller.ControllerBuilder;
import dev.tcl.api.controller.TickBoxControllerBuilder;
import dev.tcl.config.api.ConfigField;
import dev.tcl.config.api.autogen.SimpleOptionFactory;
import dev.tcl.config.api.autogen.MasterTickBox;
import dev.tcl.config.api.autogen.OptionAccess;

public class MasterTickBoxImpl extends SimpleOptionFactory<MasterTickBox, Boolean> {
    @Override
    protected ControllerBuilder<Boolean> createController(MasterTickBox annotation, ConfigField<Boolean> field, OptionAccess storage, Option<Boolean> option) {
        return TickBoxControllerBuilder.create(option);
    }

    @Override
    protected void listener(MasterTickBox annotation, ConfigField<Boolean> field, OptionAccess storage, Option<Boolean> option, Boolean value) {
        for (String child : annotation.value()) {
            storage.scheduleOptionOperation(child, childOpt -> {
                childOpt.setAvailable(annotation.invert() != value);
            });
        }
    }
}
