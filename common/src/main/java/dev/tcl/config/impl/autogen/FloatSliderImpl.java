package dev.tcl.config.impl.autogen;

import dev.tcl.api.Option;
import dev.tcl.api.controller.ControllerBuilder;
import dev.tcl.api.controller.FloatSliderControllerBuilder;
import dev.tcl.config.api.ConfigField;
import dev.tcl.config.api.autogen.SimpleOptionFactory;
import dev.tcl.config.api.autogen.FloatSlider;
import dev.tcl.config.api.autogen.OptionAccess;

public class FloatSliderImpl extends SimpleOptionFactory<FloatSlider, Float> {
    @Override
    protected ControllerBuilder<Float> createController(FloatSlider annotation, ConfigField<Float> field, OptionAccess storage, Option<Float> option) {
        return FloatSliderControllerBuilder.create(option)
                .formatValue(createMinMaxFormatter(field, annotation::min, annotation::max, annotation::format))
                .range(annotation.min(), annotation.max())
                .step(annotation.step());
    }
}
