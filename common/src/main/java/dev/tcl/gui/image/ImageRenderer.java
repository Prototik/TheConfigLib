package dev.tcl.gui.image;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;

@Environment(EnvType.CLIENT)
public interface ImageRenderer {
    int render(GuiGraphics graphics, int x, int y, int renderWidth, float tickDelta);

    void close();

    default void tick() {}
}
