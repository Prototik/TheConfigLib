package dev.tcl.api;

import dev.tcl.gui.controllers.cycling.EnumController;
import dev.tcl.gui.controllers.dropdown.EnumDropdownController;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

/**
 * Used for the default value formatter of {@link EnumController} and {@link EnumDropdownController}
 */
public interface NameableEnum {
    @NotNull Component getDisplayName();

    default @NotNull Component getDescription() {
        return Component.empty();
    }
}
