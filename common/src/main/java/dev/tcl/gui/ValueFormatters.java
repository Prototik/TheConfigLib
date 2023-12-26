package dev.tcl.gui;

import dev.tcl.api.controller.ValueFormatter;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public final class ValueFormatters {
    public static final PercentFormatter PERCENT = new PercentFormatter();

    public static final class PercentFormatter implements ValueFormatter<Float> {
        @Override
        public @NotNull Component format(@NotNull Float value) {
            return Component.literal(String.format("%.0f%%", value * 100));
        }
    }
}
