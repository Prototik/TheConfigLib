package dev.tcl.api.controller;

import dev.tcl.api.Option;
import dev.tcl.impl.controller.ColorControllerBuilderImpl;

import java.awt.Color;

public interface ColorControllerBuilder extends ControllerBuilder<Color> {
    ColorControllerBuilder allowAlpha(boolean allowAlpha);

    static ColorControllerBuilder create(Option<Color> option) {
        return new ColorControllerBuilderImpl(option);
    }
}
