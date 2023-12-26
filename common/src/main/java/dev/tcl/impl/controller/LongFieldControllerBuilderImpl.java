package dev.tcl.impl.controller;

import dev.tcl.api.Controller;
import dev.tcl.api.Option;
import dev.tcl.api.controller.LongFieldControllerBuilder;
import dev.tcl.api.controller.ValueFormatter;
import dev.tcl.gui.controllers.slider.LongSliderController;
import dev.tcl.gui.controllers.string.number.LongFieldController;
import org.jetbrains.annotations.NotNull;

public class LongFieldControllerBuilderImpl extends AbstractControllerBuilderImpl<Long> implements LongFieldControllerBuilder {
    private long min = Long.MIN_VALUE;
    private long max = Long.MAX_VALUE;
    private ValueFormatter<Long> formatter = LongSliderController.DEFAULT_FORMATTER;

    public LongFieldControllerBuilderImpl(Option<Long> option) {
        super(option);
    }

    @Override
    public LongFieldControllerBuilder min(Long min) {
        this.min = min;
        return this;
    }

    @Override
    public LongFieldControllerBuilder max(Long max) {
        this.max = max;
        return this;
    }

    @Override
    public LongFieldControllerBuilder range(Long min, Long max) {
        this.min = min;
        this.max = max;
        return this;
    }

    @Override
    public LongFieldControllerBuilder formatValue(@NotNull ValueFormatter<Long> formatter) {
        this.formatter = formatter;
        return this;
    }

    @Override
    public Controller<Long> build() {
        return new LongFieldController(option, min, max, formatter);
    }
}
