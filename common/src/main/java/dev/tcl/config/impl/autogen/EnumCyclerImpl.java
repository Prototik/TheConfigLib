package dev.tcl.config.impl.autogen;

import dev.tcl.api.Option;
import dev.tcl.api.controller.ControllerBuilder;
import dev.tcl.api.controller.CyclingListControllerBuilder;
import dev.tcl.api.controller.ValueFormatter;
import dev.tcl.config.api.ConfigField;
import dev.tcl.config.api.autogen.EnumCycler;
import dev.tcl.config.api.autogen.OptionAccess;

import java.util.List;

public class EnumCyclerImpl<E extends Enum<E>> extends AbstractEnumFactory<EnumCycler, E> {
    @Override
    protected int[] getAllowedOrdinals(EnumCycler annotation) {
        return annotation.allowedOrdinals();
    }

    @Override
    protected ControllerBuilder<E> createController(EnumCycler annotation, ConfigField<E> field, OptionAccess storage, Option<E> option, List<? extends E> values, ValueFormatter<E> valueFormatter) {
        // EnumController doesn't support filtering
        return CyclingListControllerBuilder.create(option)
                .values(values)
                .formatValue(valueFormatter);
    }
}
