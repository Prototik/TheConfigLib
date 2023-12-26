package dev.tcl.config.impl.autogen;

import dev.tcl.api.Option;
import dev.tcl.api.TheConfigLib;
import dev.tcl.config.api.autogen.OptionAccess;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class OptionAccessImpl implements OptionAccess {
    private final Map<String, Option<?>> storage = new HashMap<>();
    private final Map<String, Consumer<Option<?>>> scheduledOperations = new HashMap<>();

    @Override
    public @Nullable Option<?> getOption(String fieldName) {
        return storage.get(fieldName);
    }

    @Override
    public void scheduleOptionOperation(String fieldName, Consumer<Option<?>> optionConsumer) {
        if (storage.containsKey(fieldName)) {
            optionConsumer.accept(storage.get(fieldName));
        } else {
            scheduledOperations.merge(fieldName, optionConsumer, Consumer::andThen);
        }
    }

    public void putOption(String fieldName, Option<?> option) {
        storage.put(fieldName, option);

        Consumer<Option<?>> consumer = scheduledOperations.remove(fieldName);
        if (consumer != null) {
            consumer.accept(option);
        }
    }

    public void checkBadOperations() {
        if (!scheduledOperations.isEmpty()) {
            TheConfigLib.LOGGER.warn("There are scheduled operations on the `OptionAccess` that tried to reference fields that do not exist. The following have been referenced that do not exist: " + String.join(", ", scheduledOperations.keySet()));
        }
    }
}
