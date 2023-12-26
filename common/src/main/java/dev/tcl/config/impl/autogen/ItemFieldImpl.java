package dev.tcl.config.impl.autogen;

import dev.tcl.api.Option;
import dev.tcl.api.controller.ControllerBuilder;
import dev.tcl.api.controller.ItemControllerBuilder;
import dev.tcl.config.api.ConfigField;
import dev.tcl.config.api.autogen.ItemField;
import dev.tcl.config.api.autogen.OptionAccess;
import dev.tcl.config.api.autogen.SimpleOptionFactory;
import net.minecraft.world.item.Item;

public class ItemFieldImpl extends SimpleOptionFactory<ItemField, Item> {
	@Override
	protected ControllerBuilder<Item> createController(ItemField annotation, ConfigField<Item> field, OptionAccess storage, Option<Item> option) {
		return ItemControllerBuilder.create(option);
	}
}
