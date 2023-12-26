package dev.tcl.config.impl.autogen;

import dev.tcl.api.Option;
import dev.tcl.api.controller.ControllerBuilder;
import dev.tcl.api.controller.DropdownStringControllerBuilder;
import dev.tcl.config.api.ConfigField;
import dev.tcl.config.api.autogen.Dropdown;
import dev.tcl.config.api.autogen.OptionAccess;
import dev.tcl.config.api.autogen.SimpleOptionFactory;

public class DropdownImpl extends SimpleOptionFactory<Dropdown, String> {
	@Override
	protected ControllerBuilder<String> createController(Dropdown annotation, ConfigField<String> field, OptionAccess storage, Option<String> option) {
		return DropdownStringControllerBuilder.create(option)
				.values(annotation.values())
				.allowEmptyValue(annotation.allowEmptyValue())
				.allowAnyValue(annotation.allowAnyValue());
	}
}
