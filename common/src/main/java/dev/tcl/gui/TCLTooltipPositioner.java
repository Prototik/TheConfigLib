package dev.tcl.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;
import org.joml.Vector2ic;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class TCLTooltipPositioner implements ClientTooltipPositioner {
    private final Supplier<ScreenRectangle> buttonDimensions;

    public TCLTooltipPositioner(net.minecraft.client.gui.components.AbstractWidget widget) {
        this.buttonDimensions = widget::getRectangle;
    }

    public TCLTooltipPositioner(AbstractWidget widget) {
        this.buttonDimensions = () -> {
            var dim = widget.getDimension();
            return new ScreenRectangle(dim.x(), dim.y(), dim.width(), dim.height());
        };
    }

    public TCLTooltipPositioner(Supplier<ScreenRectangle> buttonDimensions) {
        this.buttonDimensions = buttonDimensions;
    }

    @Override
    public @NotNull Vector2ic positionTooltip(int guiWidth, int guiHeight, int x, int y, int width, int height) {
        ScreenRectangle buttonDimensions = this.buttonDimensions.get();

        int centerX = buttonDimensions.left() + buttonDimensions.width() / 2;
        int aboveY = buttonDimensions.top() - height - 4;
        int belowY = buttonDimensions.top() + buttonDimensions.height() + 4;

        int maxBelow = guiHeight - (belowY + height);
        int minAbove = aboveY - height;

        int yResult = aboveY;
        if (minAbove < 8)
            yResult = maxBelow > minAbove ? belowY : aboveY;

        int xResult = Mth.clamp(centerX - width / 2, -4, guiWidth - width - 4);

        return new Vector2i(xResult, yResult);
    }
}
