package dev.tcl.config.impl.autogen;

import dev.tcl.api.Option;
import dev.tcl.api.controller.ControllerBuilder;
import dev.tcl.api.controller.EnumDropdownControllerBuilder;
import dev.tcl.api.controller.ValueFormatter;
import dev.tcl.config.api.ConfigField;
import dev.tcl.config.api.autogen.EnumDropdown;
import dev.tcl.config.api.autogen.OptionAccess;

import java.util.List;

public class EnumDropdownImpl<E extends Enum<E>> extends AbstractEnumFactory<EnumDropdown, E> {
    @Override
    protected int[] getAllowedOrdinals(EnumDropdown annotation) {
        return annotation.allowedOrdinals();
    }

    @Override
    protected ControllerBuilder<E> createController(EnumDropdown annotation, ConfigField<E> field, OptionAccess storage, Option<E> option, List<? extends E> values, ValueFormatter<E> valueFormatter) {
        return EnumDropdownControllerBuilder.create(option)
                .values(values)
                .formatValue(valueFormatter);
    }
}
