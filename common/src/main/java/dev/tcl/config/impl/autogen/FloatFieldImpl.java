package dev.tcl.config.impl.autogen;

import dev.tcl.api.Option;
import dev.tcl.api.controller.ControllerBuilder;
import dev.tcl.api.controller.FloatFieldControllerBuilder;
import dev.tcl.config.api.ConfigField;
import dev.tcl.config.api.autogen.FloatField;
import dev.tcl.config.api.autogen.OptionAccess;
import dev.tcl.config.api.autogen.SimpleOptionFactory;

public class FloatFieldImpl extends SimpleOptionFactory<FloatField, Float> {
    @Override
    protected ControllerBuilder<Float> createController(FloatField annotation, ConfigField<Float> field, OptionAccess storage, Option<Float> option) {
        return FloatFieldControllerBuilder.create(option)
                .formatValue(createMinMaxFormatter(field, annotation::min, annotation::max, annotation::format))
                .range(annotation.min(), annotation.max());
    }
}
