package dev.tcl.api.controller;

import dev.tcl.api.Option;
import dev.tcl.impl.controller.LongFieldControllerBuilderImpl;

public interface LongFieldControllerBuilder extends NumberFieldControllerBuilder<Long, LongFieldControllerBuilder> {
    static LongFieldControllerBuilder create(Option<Long> option) {
        return new LongFieldControllerBuilderImpl(option);
    }
}
