package dev.tcl.impl.controller;

import dev.tcl.api.Controller;
import dev.tcl.api.Option;
import dev.tcl.api.controller.ItemControllerBuilder;
import dev.tcl.gui.controllers.dropdown.ItemController;
import net.minecraft.world.item.Item;

public class ItemControllerBuilderImpl extends AbstractControllerBuilderImpl<Item> implements ItemControllerBuilder {
	public ItemControllerBuilderImpl(Option<Item> option) {
		super(option);
	}

	@Override
	public Controller<Item> build() {
		return new ItemController(option);
	}
}
