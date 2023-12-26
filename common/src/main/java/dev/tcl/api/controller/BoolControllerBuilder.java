package dev.tcl.api.controller;

import dev.tcl.api.Option;
import dev.tcl.impl.controller.BoolControllerBuilderImpl;

public interface BoolControllerBuilder extends ValueFormattableController<Boolean, BoolControllerBuilder> {
    BoolControllerBuilder coloured(boolean coloured);

    BoolControllerBuilder onOffFormatter();
    BoolControllerBuilder yesNoFormatter();
    BoolControllerBuilder trueFalseFormatter();

    static BoolControllerBuilder create(Option<Boolean> option) {
        return new BoolControllerBuilderImpl(option);
    }
}
