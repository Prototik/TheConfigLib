package dev.tcl.config.impl.autogen;

import dev.tcl.api.Option;
import dev.tcl.api.controller.ControllerBuilder;
import dev.tcl.api.controller.DoubleSliderControllerBuilder;
import dev.tcl.config.api.ConfigField;
import dev.tcl.config.api.autogen.SimpleOptionFactory;
import dev.tcl.config.api.autogen.DoubleSlider;
import dev.tcl.config.api.autogen.OptionAccess;

public class DoubleSliderImpl extends SimpleOptionFactory<DoubleSlider, Double> {
    @Override
    protected ControllerBuilder<Double> createController(DoubleSlider annotation, ConfigField<Double> field, OptionAccess storage, Option<Double> option) {
        return DoubleSliderControllerBuilder.create(option)
                .formatValue(createMinMaxFormatter(field, annotation::min, annotation::max, annotation::format))
                .range(annotation.min(), annotation.max())
                .step(annotation.step());
    }
}
