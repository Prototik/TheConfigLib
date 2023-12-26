package dev.tcl.impl.controller;

import dev.tcl.api.Controller;
import dev.tcl.api.Option;
import dev.tcl.api.controller.LongSliderControllerBuilder;
import dev.tcl.api.controller.ValueFormatter;
import dev.tcl.gui.controllers.slider.LongSliderController;
import org.jetbrains.annotations.NotNull;

public class LongSliderControllerBuilderImpl extends AbstractControllerBuilderImpl<Long> implements LongSliderControllerBuilder {
    private long min, max;
    private long step;
    private ValueFormatter<Long> formatter = LongSliderController.DEFAULT_FORMATTER;

    public LongSliderControllerBuilderImpl(Option<Long> option) {
        super(option);
    }

    @Override
    public LongSliderControllerBuilder range(Long min, Long max) {
        this.min = min;
        this.max = max;
        return this;
    }

    @Override
    public LongSliderControllerBuilder step(Long step) {
        this.step = step;
        return this;
    }

    @Override
    public LongSliderControllerBuilder formatValue(@NotNull ValueFormatter<Long> formatter) {
        this.formatter = formatter;
        return this;
    }

    @Override
    public Controller<Long> build() {
        return new LongSliderController(option, min, max, step, formatter);
    }
}
