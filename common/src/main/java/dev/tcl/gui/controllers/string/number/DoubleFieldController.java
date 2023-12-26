package dev.tcl.gui.controllers.string.number;

import dev.tcl.api.Option;
import dev.tcl.api.controller.ValueFormatter;
import dev.tcl.gui.controllers.slider.DoubleSliderController;

/**
 * {@inheritDoc}
 */
public class DoubleFieldController extends NumberFieldController<Double> {
    private final double min, max;

    /**
     * Constructs a double field controller
     *
     * @param option option to bind controller to
     * @param min minimum allowed value (clamped on apply)
     * @param max maximum allowed value (clamped on apply)
     * @param formatter display text, not used whilst editing
     */
    public DoubleFieldController(Option<Double> option, double min, double max, ValueFormatter<Double> formatter) {
        super(option, formatter);
        this.min = min;
        this.max = max;
    }

    /**
     * Constructs a double field controller.
     * Uses {@link DoubleSliderController#DEFAULT_FORMATTER} as display text,
     * not used whilst editing.
     *
     * @param option option to bind controller to
     * @param min minimum allowed value (clamped on apply)
     * @param max maximum allowed value (clamped on apply)
     */
    public DoubleFieldController(Option<Double> option, double min, double max) {
        this(option, min, max, DoubleSliderController.DEFAULT_FORMATTER);
    }

    /**
     * Constructs a double field controller.
     * Does not have a minimum or a maximum range.
     *
     * @param option option to bind controller to
     * @param formatter display text, not used whilst editing
     */
    public DoubleFieldController(Option<Double> option, ValueFormatter<Double> formatter) {
        this(option, -Double.MAX_VALUE, Double.MAX_VALUE, formatter);
    }

    /**
     * Constructs a double field controller.
     * Uses {@link DoubleSliderController#DEFAULT_FORMATTER} as display text,
     * not used whilst editing.
     * Does not have a minimum or a maximum range.
     *
     * @param option option to bind controller to
     */
    public DoubleFieldController(Option<Double> option) {
        this(option, -Double.MAX_VALUE, Double.MAX_VALUE, DoubleSliderController.DEFAULT_FORMATTER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double min() {
        return this.min;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double max() {
        return this.max;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getString() {
        return NUMBER_FORMAT.format(option().pendingValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPendingValue(double value) {
        option().requestSet(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double pendingValue() {
        return option().pendingValue();
    }
}
