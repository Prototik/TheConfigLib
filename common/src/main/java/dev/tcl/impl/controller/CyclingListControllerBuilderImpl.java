package dev.tcl.impl.controller;

import com.google.common.collect.ImmutableList;
import dev.tcl.api.Controller;
import dev.tcl.api.Option;
import dev.tcl.api.controller.CyclingListControllerBuilder;
import dev.tcl.api.controller.ValueFormatter;
import dev.tcl.gui.controllers.cycling.CyclingListController;
import org.jetbrains.annotations.NotNull;

public final class CyclingListControllerBuilderImpl<T> extends AbstractControllerBuilderImpl<T> implements CyclingListControllerBuilder<T> {
    private Iterable<? extends T> values;
    private ValueFormatter<T> formatter = null;

    public CyclingListControllerBuilderImpl(Option<T> option) {
        super(option);
    }

    @Override
    public CyclingListControllerBuilder<T> values(Iterable<? extends T> values) {
        this.values = values;
        return this;
    }

    @SafeVarargs
    @Override
    public final CyclingListControllerBuilder<T> values(T... values) {
        this.values = ImmutableList.copyOf(values);
        return this;
    }

    @Override
    public CyclingListControllerBuilder<T> formatValue(@NotNull ValueFormatter<T> formatter) {
        this.formatter = formatter;
        return this;
    }

    @Override
    public Controller<T> build() {
        return new CyclingListController<>(option, values, formatter);
    }
}
