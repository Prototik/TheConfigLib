package dev.tcl.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class TCLTooltip extends Tooltip {
    private final net.minecraft.client.gui.components.AbstractWidget widget;

    public TCLTooltip(Component tooltip, net.minecraft.client.gui.components.AbstractWidget widget) {
        super(tooltip, tooltip);
        this.widget = widget;
    }

    @NotNull
    @Override
    protected ClientTooltipPositioner createTooltipPositioner(boolean bl, boolean bl2, ScreenRectangle screenRectangle) {
        return new TCLTooltipPositioner(widget);
    }
}
