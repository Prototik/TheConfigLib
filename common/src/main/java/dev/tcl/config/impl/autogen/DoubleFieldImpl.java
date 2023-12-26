package dev.tcl.config.impl.autogen;

import dev.tcl.api.Option;
import dev.tcl.api.controller.ControllerBuilder;
import dev.tcl.api.controller.DoubleFieldControllerBuilder;
import dev.tcl.config.api.ConfigField;
import dev.tcl.config.api.autogen.DoubleField;
import dev.tcl.config.api.autogen.OptionAccess;
import dev.tcl.config.api.autogen.SimpleOptionFactory;

public class DoubleFieldImpl extends SimpleOptionFactory<DoubleField, Double> {
    @Override
    protected ControllerBuilder<Double> createController(DoubleField annotation, ConfigField<Double> field, OptionAccess storage, Option<Double> option) {
        return DoubleFieldControllerBuilder.create(option)
                .formatValue(createMinMaxFormatter(field, annotation::min, annotation::max, annotation::format))
                .range(annotation.min(), annotation.max());
    }
}
