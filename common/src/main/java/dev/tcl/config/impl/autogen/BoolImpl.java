package dev.tcl.config.impl.autogen;

import dev.tcl.api.Option;
import dev.tcl.api.controller.ControllerBuilder;
import dev.tcl.api.controller.BoolControllerBuilder;
import dev.tcl.config.api.ConfigField;
import dev.tcl.config.api.autogen.Bool;
import dev.tcl.config.api.autogen.OptionAccess;
import dev.tcl.config.api.autogen.SimpleOptionFactory;
import net.minecraft.network.chat.Component;

public class BoolImpl extends SimpleOptionFactory<Bool, Boolean> {
    @Override
    protected ControllerBuilder<Boolean> createController(Bool annotation, ConfigField<Boolean> field, OptionAccess storage, Option<Boolean> option) {
        var builder = BoolControllerBuilder.create(option)
                .coloured(annotation.colored());
        switch (annotation.formatter()) {
            case ON_OFF -> builder.onOffFormatter();
            case YES_NO -> builder.yesNoFormatter();
            case TRUE_FALSE -> builder.trueFalseFormatter();
            case CUSTOM -> builder.formatValue(v -> Component.translatable(getTranslationKey(field, "fmt." + v)));
        }
        return builder;
    }
}
