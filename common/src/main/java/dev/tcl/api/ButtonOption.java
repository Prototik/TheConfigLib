package dev.tcl.api;

import dev.tcl.gui.TCLScreen;
import dev.tcl.impl.ButtonOptionImpl;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public interface ButtonOption extends Option<BiConsumer<TCLScreen, ButtonOption>> {
    /**
     * Action to be executed upon button press
     */
    BiConsumer<TCLScreen, ButtonOption> action();

    static Builder createBuilder() {
        return new ButtonOptionImpl.BuilderImpl();
    }

    interface Builder {
        /**
         * Sets the name to be used by the option.
         *
         * @see Option#name()
         */
        Builder name(@NotNull Component name);

        /**
         * Sets the button text to be displayed next to the name.
         */
        Builder text(@NotNull Component text);

        Builder description(@NotNull OptionDescription description);

        /**
         * Action to be executed upon button press
         *
         * @see ButtonOption#action()
         */
        Builder action(@NotNull BiConsumer<TCLScreen, ButtonOption> action);

        /**
         * Sets if the option can be configured
         *
         * @see Option#available()
         */
        Builder available(boolean available);

        ButtonOption build();
    }
}
