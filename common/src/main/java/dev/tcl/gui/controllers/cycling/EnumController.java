package dev.tcl.gui.controllers.cycling;

import dev.tcl.api.NameableEnum;
import dev.tcl.api.Option;
import dev.tcl.api.controller.ValueFormatter;
import net.minecraft.network.chat.Component;
import net.minecraft.util.OptionEnum;

import java.util.Arrays;

/**
 * Simple controller type that displays the enum on the right.
 * <p>
 * Cycles forward with left click, cycles backward with right click or when shift is held
 *
 * @param <T> enum type
 */
public class EnumController<T extends Enum<T>> extends CyclingListController<T> {
    public static <T extends Enum<T>> ValueFormatter<T> getDefaultFormatter() {
        return value -> {
            if (value instanceof NameableEnum nameableEnum)
                return nameableEnum.getDisplayName();
            if (value instanceof OptionEnum translatableOption)
                return translatableOption.getCaption();
            return Component.literal(value.toString());
        };
    }

    public EnumController(Option<T> option, Class<T> enumClass) {
        this(option, getDefaultFormatter(), enumClass.getEnumConstants());
    }

    /**
     * Constructs a cycling enum controller.
     *
     * @param option bound option
     * @param valueFormatter format the enum into any {@link Component}
     * @param availableValues all enum constants that can be cycled through
     */
    public EnumController(Option<T> option, ValueFormatter<T> valueFormatter, T[] availableValues) {
        super(option, Arrays.asList(availableValues), valueFormatter);
    }
}
