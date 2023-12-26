package dev.tcl.api.controller;

import dev.tcl.api.Controller;
import org.jetbrains.annotations.ApiStatus;

@FunctionalInterface
public interface ControllerBuilder<T> {
    @ApiStatus.Internal
    Controller<T> build();
}
