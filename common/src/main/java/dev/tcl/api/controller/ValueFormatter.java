package dev.tcl.api.controller;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

@FunctionalInterface
public interface ValueFormatter<T> extends Function<T, Component> {
    @NotNull Component format(@NotNull T value);

    @Override
    default @NotNull Component apply(@NotNull T t) {
        return format(t);
    }
}
