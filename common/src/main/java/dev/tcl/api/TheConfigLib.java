package dev.tcl.api;

import com.google.common.collect.ImmutableList;
import dev.tcl.config.api.ConfigClassHandler;
import dev.tcl.gui.TCLScreen;
import dev.tcl.impl.TheConfigLibImpl;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * Main class of the mod.
 * Contains all data and used to provide a {@link Screen}
 */
public interface TheConfigLib {
    String MOD_ID = "the_config_lib";
    String FULL_NAME = "The Config Lib";
    String SHORT_NAME = "TCL";

    Logger LOGGER = LoggerFactory.getLogger(TheConfigLib.class);

    /**
     * Title of the GUI. Only used for Minecraft narration.
     */
    Component title();

    /**
     * Gets all config categories.
     */
    ImmutableList<ConfigCategory> categories();

    /**
     * Ran when changes are saved. Can be used to save config to a file etc.
     */
    Runnable saveFunction();

    /**
     * Ran every time the TCL screen initialises. Can be paired with FAPI to add custom widgets.
     */
    Consumer<TCLScreen> initConsumer();

    /**
     * Generates a Screen to display based on this instance.
     *
     * @param parent parent screen to open once closed
     */
    Screen generateScreen(@Nullable Screen parent);

    /**
     * Creates a builder to construct TCL
     */
    static @NotNull Builder createBuilder() {
        return new TheConfigLibImpl.BuilderImpl();
    }

    static <T> TheConfigLib create(@NotNull ConfigClassHandler<T> configHandler, @NotNull ConfigBackedBuilder<T> builder) {
        return builder.build(configHandler.defaults(), configHandler.instance(), createBuilder().save(configHandler::save)).build();
    }

    interface Builder {
        /**
         * Sets title of GUI for Minecraft narration
         *
         * @see TheConfigLib#title()
         */
        Builder title(@NotNull Component title);

        /**
         * Adds a new category.
         * To create a category you need to use {@link ConfigCategory#createBuilder()}
         *
         * @see TheConfigLib#categories()
         */
        Builder category(@NotNull ConfigCategory category);

        /**
         * Adds multiple categories at once.
         * To create a category you need to use {@link ConfigCategory#createBuilder()}
         *
         * @see TheConfigLib#categories()
         */
        Builder categories(@NotNull Collection<? extends ConfigCategory> categories);

        /**
         * Used to define a save function for when user clicks the Save Changes button
         *
         * @see TheConfigLib#saveFunction()
         */
        Builder save(@NotNull Runnable saveFunction);

        /**
         * Defines a consumer that is accepted every time the TCL screen initialises
         *
         * @see TheConfigLib#initConsumer()
         */
        Builder screenInit(@NotNull Consumer<TCLScreen> initConsumer);

        TheConfigLib build();
    }

    @FunctionalInterface
    interface ConfigBackedBuilder<T> {
        TheConfigLib.Builder build(T defaults, T config, TheConfigLib.Builder builder);
    }
}
