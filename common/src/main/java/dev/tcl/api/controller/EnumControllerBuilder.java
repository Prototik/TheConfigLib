package dev.tcl.api.controller;

import dev.tcl.api.Option;
import dev.tcl.impl.controller.EnumControllerBuilderImpl;

public interface EnumControllerBuilder<T extends Enum<T>> extends ValueFormattableController<T, EnumControllerBuilder<T>> {
    EnumControllerBuilder<T> enumClass(Class<T> enumClass);

    static <T extends Enum<T>> EnumControllerBuilder<T> create(Option<T> option) {
        return new EnumControllerBuilderImpl<>(option);
    }
}
