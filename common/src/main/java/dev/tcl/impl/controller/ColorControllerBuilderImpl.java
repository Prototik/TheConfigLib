package dev.tcl.impl.controller;

import dev.tcl.api.Controller;
import dev.tcl.api.Option;
import dev.tcl.api.controller.ColorControllerBuilder;
import dev.tcl.gui.controllers.ColorController;

import java.awt.Color;

public class ColorControllerBuilderImpl extends AbstractControllerBuilderImpl<Color> implements ColorControllerBuilder {
    private boolean allowAlpha = false;

    public ColorControllerBuilderImpl(Option<Color> option) {
        super(option);
    }

    @Override
    public ColorControllerBuilder allowAlpha(boolean allowAlpha) {
        this.allowAlpha = allowAlpha;
        return this;
    }

    @Override
    public Controller<Color> build() {
        return new ColorController(option, allowAlpha);
    }
}
