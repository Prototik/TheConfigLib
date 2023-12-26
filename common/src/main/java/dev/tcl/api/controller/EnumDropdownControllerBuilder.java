package dev.tcl.api.controller;

import dev.tcl.api.Option;
import dev.tcl.impl.controller.EnumDropdownControllerBuilderImpl;

public interface EnumDropdownControllerBuilder<T extends Enum<T>> extends ValueFormattableController<T, EnumDropdownControllerBuilder<T>> {
    @SuppressWarnings("unchecked")
    EnumDropdownControllerBuilder<T> values(T... values);

    EnumDropdownControllerBuilder<T> values(Iterable<? extends T> values);

    static <E extends Enum<E>> EnumDropdownControllerBuilder<E> create(Option<E> option) {
        return new EnumDropdownControllerBuilderImpl<>(option);
    }
}
