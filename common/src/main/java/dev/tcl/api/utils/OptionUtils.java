package dev.tcl.api.utils;

import dev.tcl.api.*;

import java.util.function.Consumer;
import java.util.function.Function;

public class OptionUtils {
    /**
     * Consumes all options, ignoring groups and categories.
     * When consumer returns true, this function stops iterating.
     */
    public static void consumeOptions(TheConfigLib lib, Function<Option<?>, Boolean> consumer) {
        for (ConfigCategory category : lib.categories()) {
            for (OptionGroup group : category.groups()) {
                if (group instanceof ListOption<?> list) {
                    if (consumer.apply(list)) return;
                } else {
                    for (Option<?> option : group.options()) {
                        if (consumer.apply(option)) return;
                    }
                }
            }
        }
    }

    /**
     * Consumes all options, ignoring groups and categories.
     *
     * @see OptionUtils#consumeOptions(TheConfigLib, Function)
     */
    public static void forEachOptions(TheConfigLib lib, Consumer<Option<?>> consumer) {
        consumeOptions(lib, (opt) -> {
            consumer.accept(opt);
            return false;
        });
    }
}
