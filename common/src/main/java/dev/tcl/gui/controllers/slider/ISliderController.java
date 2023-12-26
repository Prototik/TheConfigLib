package dev.tcl.gui.controllers.slider;

import dev.tcl.api.Controller;
import dev.tcl.api.Option;
import dev.tcl.api.utils.Dimension;
import dev.tcl.gui.AbstractWidget;
import dev.tcl.gui.TCLScreen;

/**
 * Simple custom slider implementation that shifts the current value across when shown.
 * <p>
 * For simplicity, {@link SliderControllerElement} works in doubles so each
 * {@link ISliderController} must cast to double. This is to get around re-writing the element for every type.
 */
public interface ISliderController<T extends Number> extends Controller<T> {
    /**
     * Gets the minimum value for the slider
     */
    double min();

    /**
     * Gets the maximum value for the slider
     */
    double max();

    /**
     * Gets the interval (or step size) for the slider.
     */
    double interval();

    /**
     * Gets the range of the slider.
     */
    default double range() {
        return max() - min();
    }

    /**
     * Sets the {@link Option}'s pending value
     */
    void setPendingValue(double value);

    /**
     * Gets the {@link Option}'s pending value
     */
    double pendingValue();

    /**
     * {@inheritDoc}
     */
    @Override
    default AbstractWidget provideWidget(TCLScreen screen, Dimension<Integer> widgetDimension) {
        return new SliderControllerElement(this, screen, widgetDimension, min(), max(), interval());
    }
}
