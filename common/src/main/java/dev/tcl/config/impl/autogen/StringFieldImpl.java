package dev.tcl.config.impl.autogen;

import dev.tcl.api.Option;
import dev.tcl.api.controller.ControllerBuilder;
import dev.tcl.api.controller.StringControllerBuilder;
import dev.tcl.config.api.ConfigField;
import dev.tcl.config.api.autogen.SimpleOptionFactory;
import dev.tcl.config.api.autogen.OptionAccess;
import dev.tcl.config.api.autogen.StringField;

public class StringFieldImpl extends SimpleOptionFactory<StringField, String> {
    @Override
    protected ControllerBuilder<String> createController(StringField annotation, ConfigField<String> field, OptionAccess storage, Option<String> option) {
        return StringControllerBuilder.create(option);
    }
}
