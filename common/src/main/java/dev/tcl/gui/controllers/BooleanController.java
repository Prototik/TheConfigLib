package dev.tcl.gui.controllers;

import com.mojang.blaze3d.platform.InputConstants;
import dev.tcl.api.Controller;
import dev.tcl.api.Option;
import dev.tcl.api.utils.Dimension;
import dev.tcl.api.controller.ValueFormatter;
import dev.tcl.gui.AbstractWidget;
import dev.tcl.gui.TCLScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

/**
 * This controller renders a simple formatted {@link Component}
 */
public class BooleanController implements Controller<Boolean> {

    public static final ValueFormatter<Boolean> ON_OFF_FORMATTER = (state) ->
            state ? CommonComponents.OPTION_ON : CommonComponents.OPTION_OFF;

    public static final ValueFormatter<Boolean> TRUE_FALSE_FORMATTER = (state) ->
            state ? Component.translatable("tcl.control.boolean.true") : Component.translatable("tcl.control.boolean.false");

    public static final ValueFormatter<Boolean> YES_NO_FORMATTER = (state) ->
            state ? CommonComponents.GUI_YES : CommonComponents.GUI_NO;

    private final Option<Boolean> option;
    private final ValueFormatter<Boolean> valueFormatter;
    private final boolean coloured;

    /**
     * Constructs a tickbox controller
     * with the default value formatter of {@link BooleanController#ON_OFF_FORMATTER}
     *
     * @param option bound option
     */
    public BooleanController(Option<Boolean> option) {
        this(option, ON_OFF_FORMATTER, false);
    }

    /**
     * Constructs a tickbox controller
     * with the default value formatter of {@link BooleanController#ON_OFF_FORMATTER}
     *
     * @param option bound option
     * @param coloured value format is green or red depending on the state
     */
    public BooleanController(Option<Boolean> option, boolean coloured) {
        this(option, ON_OFF_FORMATTER, coloured);
    }

    /**
     * Constructs a tickbox controller
     *
     * @param option bound option
     * @param valueFormatter format value into any {@link Component}
     * @param coloured value format is green or red depending on the state
     */
    public BooleanController(Option<Boolean> option, ValueFormatter<Boolean> valueFormatter, boolean coloured) {
        this.option = option;
        this.valueFormatter = valueFormatter;
        this.coloured = coloured;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Option<Boolean> option() {
        return option;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Component formatValue() {
        return valueFormatter.format(option().pendingValue());
    }

    /**
     * Value format is green or red depending on the state
     */
    public boolean coloured() {
        return coloured;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AbstractWidget provideWidget(TCLScreen screen, Dimension<Integer> widgetDimension) {
        return new BooleanControllerElement(this, screen, widgetDimension);
    }

    public static class BooleanControllerElement extends ControllerWidget<BooleanController> {
        public BooleanControllerElement(BooleanController control, TCLScreen screen, Dimension<Integer> dim) {
            super(control, screen, dim);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (!isMouseOver(mouseX, mouseY) || !isAvailable())
                return false;

            toggleSetting();
            return true;
        }

        @Override
        protected int getHoveredControlWidth() {
            return getUnhoveredControlWidth();
        }

        public void toggleSetting() {
            control.option().requestSet(!control.option().pendingValue());
            playDownSound();
        }

        @Override
        protected Component getValueText() {
            if (control.coloured()) {
                return super.getValueText().copy().withStyle(control.option().pendingValue() ? ChatFormatting.GREEN : ChatFormatting.RED);
            }

            return super.getValueText();
        }

        @Override
        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            if (!isFocused()) {
                return false;
            }

            if (keyCode == InputConstants.KEY_RETURN || keyCode == InputConstants.KEY_SPACE || keyCode == InputConstants.KEY_NUMPADENTER) {
                toggleSetting();
                return true;
            }

            return false;
        }
    }
}
