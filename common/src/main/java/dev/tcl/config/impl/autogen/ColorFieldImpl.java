package dev.tcl.config.impl.autogen;

import dev.tcl.api.Option;
import dev.tcl.api.controller.ColorControllerBuilder;
import dev.tcl.api.controller.ControllerBuilder;
import dev.tcl.config.api.ConfigField;
import dev.tcl.config.api.autogen.SimpleOptionFactory;
import dev.tcl.config.api.autogen.ColorField;
import dev.tcl.config.api.autogen.OptionAccess;

import java.awt.Color;

public class ColorFieldImpl extends SimpleOptionFactory<ColorField, Color> {
    @Override
    protected ControllerBuilder<Color> createController(ColorField annotation, ConfigField<Color> field, OptionAccess storage, Option<Color> option) {
        return ColorControllerBuilder.create(option)
                .allowAlpha(annotation.allowAlpha());
    }
}
