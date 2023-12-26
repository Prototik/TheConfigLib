package dev.tcl.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public class TooltipButtonWidget extends Button {

    protected final Screen screen;

    public TooltipButtonWidget(Screen screen, int x, int y, int width, int height, Component message, Component tooltip, OnPress onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
        this.screen = screen;
        if (tooltip != null)
            setTooltip(new TCLTooltip(tooltip, this));
    }
}
