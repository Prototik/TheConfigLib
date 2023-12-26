package dev.tcl.api.controller;

import org.jetbrains.annotations.NotNull;

public interface ValueFormattableController<T, B extends ValueFormattableController<T, B>> extends ControllerBuilder<T> {
    B formatValue(@NotNull ValueFormatter<T> formatter);
}
