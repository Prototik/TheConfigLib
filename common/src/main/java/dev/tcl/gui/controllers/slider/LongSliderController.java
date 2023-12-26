package dev.tcl.gui.controllers.slider;

import dev.tcl.api.Option;
import dev.tcl.api.controller.ValueFormatter;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.Validate;

/**
 * {@link ISliderController} for longs.
 */
public class LongSliderController implements ISliderController<Long> {
    public static final ValueFormatter<Long> DEFAULT_FORMATTER = value -> Component.literal(String.format("%,d", value).replaceAll("[\u00a0\u202F]", " "));

    private final Option<Long> option;

    private final long min, max, interval;

    private final ValueFormatter<Long> valueFormatter;

    /**
     * Constructs a {@link ISliderController} for longs
     * using the default value formatter {@link LongSliderController#DEFAULT_FORMATTER}.
     *
     * @param option bound option
     * @param min minimum slider value
     * @param max maximum slider value
     * @param interval step size (or increments) for the slider
     */
    public LongSliderController(Option<Long> option, long min, long max, long interval) {
        this(option, min, max, interval, DEFAULT_FORMATTER);
    }

    /**
     * Constructs a {@link ISliderController} for longs.
     *
     * @param option bound option
     * @param min minimum slider value
     * @param max maximum slider value
     * @param interval step size (or increments) for the slider
     * @param valueFormatter format the value into any {@link Component}
     */
    public LongSliderController(Option<Long> option, long min, long max, long interval, ValueFormatter<Long> valueFormatter) {
        Validate.isTrue(max > min, "`max` cannot be smaller than `min`");
        Validate.isTrue(interval > 0, "`interval` must be more than 0");
        Validate.notNull(valueFormatter, "`valueFormatter` must not be null");

        this.option = option;
        this.min = min;
        this.max = max;
        this.interval = interval;
        this.valueFormatter = valueFormatter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Option<Long> option() {
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
     * {@inheritDoc}
     */
    @Override
    public double min() {
        return min;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double max() {
        return max;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double interval() {
        return interval;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPendingValue(double value) {
        option().requestSet((long) value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double pendingValue() {
        return option().pendingValue();
    }

}
