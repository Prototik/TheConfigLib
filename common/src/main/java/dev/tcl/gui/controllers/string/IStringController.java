package dev.tcl.gui.controllers.string;

import dev.tcl.api.Controller;
import dev.tcl.api.Option;
import dev.tcl.api.utils.Dimension;
import dev.tcl.gui.AbstractWidget;
import dev.tcl.gui.TCLScreen;
import net.minecraft.network.chat.Component;

/**
 * A controller that can be any type but can input and output a string.
 */
public interface IStringController<T> extends Controller<T> {
    /**
     * Gets the option's pending value as a string.
     *
     * @see Option#pendingValue()
     */
    String getString();

    /**
     * Sets the option's pending value from a string.
     *
     * @see Option#requestSet(Object)
     */
    void setFromString(String value);

    /**
     * {@inheritDoc}
     */
    @Override
    default Component formatValue() {
        return Component.literal(getString());
    }

    default boolean isInputValid(String input) {
        return true;
    }

    @Override
    default AbstractWidget provideWidget(TCLScreen screen, Dimension<Integer> widgetDimension) {
        return new StringControllerElement(this, screen, widgetDimension, true);
    }
}
