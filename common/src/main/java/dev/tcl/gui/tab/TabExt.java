package dev.tcl.gui.tab;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.tabs.Tab;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public interface TabExt extends Tab {
    @Nullable Tooltip getTooltip();

    default void tick() {}
}
