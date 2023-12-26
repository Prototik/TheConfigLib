package dev.tcl.gui.controllers.dropdown;

import dev.tcl.api.utils.Dimension;
import dev.tcl.gui.TCLScreen;

import java.util.List;

public class EnumDropdownControllerElement<E extends Enum<E>> extends AbstractDropdownControllerElement<E, String> {
    private final EnumDropdownController<E> controller;

    public EnumDropdownControllerElement(EnumDropdownController<E> control, TCLScreen screen, Dimension<Integer> dim) {
        super(control, screen, dim);
        this.controller = control;
    }

    @Override
    public List<String> computeMatchingValues() {
        return controller.getValidEnumConstants(inputField).toList();
    }

    @Override
    public String getString(String object) {
        return object;
    }
}
