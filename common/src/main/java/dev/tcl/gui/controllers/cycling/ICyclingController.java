package dev.tcl.gui.controllers.cycling;

import dev.tcl.api.Controller;
import dev.tcl.api.utils.Dimension;
import dev.tcl.gui.AbstractWidget;
import dev.tcl.gui.TCLScreen;

/**
 * This interface simply generifies setting and getting of
 * the pending value, using an ordinal so elements can cycle through
 * without knowing the content.
 */
public interface ICyclingController<T> extends Controller<T> {
    /**
     * Sets the pending value to whatever corresponds to the ordinal
     * @param ordinal index of element to set
     */
    void setPendingValue(int ordinal);

    /**
     * Gets the pending ordinal that corresponds to the actual value
     * @return ordinal
     */
    int getPendingValue();

    /**
     * Allows the element when it should wrap-around back to zeroth ordinal
     */
    int getCycleLength();

    /**
     * {@inheritDoc}
     */
    @Override
    default AbstractWidget provideWidget(TCLScreen screen, Dimension<Integer> widgetDimension) {
        return new CyclingControllerElement(this, screen, widgetDimension);
    }
}
