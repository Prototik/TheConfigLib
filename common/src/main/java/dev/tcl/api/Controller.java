package dev.tcl.api;

import dev.tcl.api.utils.Dimension;
import dev.tcl.gui.AbstractWidget;
import dev.tcl.gui.TCLScreen;
import net.minecraft.network.chat.Component;

/**
 * Provides a widget to control the option.
 */
public interface Controller<T> {
    /**
     * Gets the dedicated {@link Option} for this controller
     */
    Option<T> option();

    /**
     * Gets the formatted value based on {@link Option#pendingValue()}
     */
    Component formatValue();

    /**
     * Provides a widget to display
     *
     * @param screen parent screen
     */
    AbstractWidget provideWidget(TCLScreen screen, Dimension<Integer> widgetDimension);
}
