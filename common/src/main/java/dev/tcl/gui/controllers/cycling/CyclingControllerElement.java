package dev.tcl.gui.controllers.cycling;

import com.mojang.blaze3d.platform.InputConstants;
import dev.tcl.api.utils.Dimension;
import dev.tcl.gui.TCLScreen;
import dev.tcl.gui.controllers.ControllerWidget;
import net.minecraft.client.gui.screens.Screen;

public class CyclingControllerElement extends ControllerWidget<ICyclingController<?>> {

    public CyclingControllerElement(ICyclingController<?> control, TCLScreen screen, Dimension<Integer> dim) {
        super(control, screen, dim);
    }

    public void cycleValue(int increment) {
        int targetIdx = control.getPendingValue() + increment;
        if (targetIdx >= control.getCycleLength()) {
            targetIdx -= control.getCycleLength();
        } else if (targetIdx < 0) {
            targetIdx += control.getCycleLength();
        }
        control.setPendingValue(targetIdx);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!isMouseOver(mouseX, mouseY) || (button != 0 && button != 1) || !isAvailable())
            return false;

        playDownSound();
        cycleValue(button == 1 || Screen.hasShiftDown() || Screen.hasControlDown() ? -1 : 1);

        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!focused)
            return false;

        switch (keyCode) {
            case InputConstants.KEY_LEFT ->
                    cycleValue(-1);
            case InputConstants.KEY_RIGHT ->
                    cycleValue(1);
            case InputConstants.KEY_RETURN, InputConstants.KEY_SPACE, InputConstants.KEY_NUMPADENTER ->
                    cycleValue(Screen.hasControlDown() || Screen.hasShiftDown() ? -1 : 1);
            default -> {
                return false;
            }
        }

        return true;
    }

    @Override
    protected int getHoveredControlWidth() {
        return getUnhoveredControlWidth();
    }
}
