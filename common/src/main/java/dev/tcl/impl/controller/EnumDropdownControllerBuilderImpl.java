package dev.tcl.impl.controller;

import com.google.common.collect.ImmutableList;
import dev.tcl.api.Controller;
import dev.tcl.api.Option;
import dev.tcl.api.controller.EnumDropdownControllerBuilder;
import dev.tcl.api.controller.ValueFormatter;
import dev.tcl.gui.controllers.cycling.EnumController;
import dev.tcl.gui.controllers.dropdown.EnumDropdownController;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class EnumDropdownControllerBuilderImpl<E extends Enum<E>> extends AbstractControllerBuilderImpl<E> implements EnumDropdownControllerBuilder<E> {
    private ValueFormatter<E> formatter = EnumController.getDefaultFormatter();
    private Collection<? extends E> values;

    public EnumDropdownControllerBuilderImpl(Option<E> option) {
        super(option);
    }

    @Override
    public EnumDropdownControllerBuilder<E> formatValue(@NotNull ValueFormatter<E> formatter) {
        this.formatter = formatter;
        return this;
    }

    @Override
    public EnumDropdownControllerBuilder<E> values(Iterable<? extends E> values) {
        if (values instanceof Collection<? extends E> valuesCollection) {
            this.values = valuesCollection;
        } else {
            this.values = ImmutableList.copyOf(values);
        }
        return this;
    }

    @SafeVarargs
    @Override
    public final EnumDropdownControllerBuilder<E> values(E... values) {
        this.values = ImmutableList.copyOf(values);
        return this;
    }

    @Override
    public Controller<E> build() {
        return new EnumDropdownController<>(option, formatter, values);
    }
}
