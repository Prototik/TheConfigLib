package dev.tcl.api.controller;

import dev.tcl.api.Option;
import dev.tcl.impl.controller.ItemControllerBuilderImpl;
import net.minecraft.world.item.Item;

public interface ItemControllerBuilder extends ControllerBuilder<Item> {
	static ItemControllerBuilder create(Option<Item> option) {
		return new ItemControllerBuilderImpl(option);
	}
}
