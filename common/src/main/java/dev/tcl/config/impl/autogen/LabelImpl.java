package dev.tcl.config.impl.autogen;

import dev.tcl.api.LabelOption;
import dev.tcl.api.Option;
import dev.tcl.config.api.ConfigField;
import dev.tcl.config.api.autogen.OptionFactory;
import dev.tcl.config.api.autogen.Label;
import dev.tcl.config.api.autogen.OptionAccess;
import net.minecraft.network.chat.Component;

public class LabelImpl implements OptionFactory<Label, Component> {
    @Override
    public Option<Component> createOption(Label annotation, ConfigField<Component> field, OptionAccess optionAccess) {
        return LabelOption.create(field.access().get());
    }
}
