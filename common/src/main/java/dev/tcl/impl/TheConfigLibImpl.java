package dev.tcl.impl;

import com.google.common.collect.ImmutableList;
import dev.tcl.api.ConfigCategory;
import dev.tcl.api.PlaceholderCategory;
import dev.tcl.api.TheConfigLib;
import dev.tcl.gui.TCLScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

@ApiStatus.Internal
public final class TheConfigLibImpl implements TheConfigLib {
    private final Component title;
    private final ImmutableList<ConfigCategory> categories;
    private final Runnable saveFunction;
    private final Consumer<TCLScreen> initConsumer;

    private boolean generated = false;

    public TheConfigLibImpl(Component title, ImmutableList<ConfigCategory> categories, Runnable saveFunction, Consumer<TCLScreen> initConsumer) {
        this.title = title;
        this.categories = categories;
        this.saveFunction = saveFunction;
        this.initConsumer = initConsumer;
    }

    @Override
    public Screen generateScreen(Screen parent) {
        if (generated)
            throw new UnsupportedOperationException("To prevent memory leaks, you should only generate a Screen once per instance. Please re-build the instance to generate another GUI.");

        TheConfigLib.LOGGER.info("Generating {} screen", TheConfigLib.SHORT_NAME);
        generated = true;
        return new TCLScreen(this, parent);
    }

    @Override
    public Component title() {
        return title;
    }

    @Override
    public ImmutableList<ConfigCategory> categories() {
        return categories;
    }

    @Override
    public Runnable saveFunction() {
        return saveFunction;
    }

    @Override
    public Consumer<TCLScreen> initConsumer() {
        return initConsumer;
    }

    @ApiStatus.Internal
    public static final class BuilderImpl implements Builder {
        private Component title;
        private final List<ConfigCategory> categories = new ArrayList<>();
        private Runnable saveFunction = () -> {};
        private Consumer<TCLScreen> initConsumer = screen -> {
        };

        @Override
        public Builder title(@NotNull Component title) {
            Validate.notNull(title, "`title` cannot be null");

            this.title = title;
            return this;
        }

        @Override
        public Builder category(@NotNull ConfigCategory category) {
            Validate.notNull(category, "`category` cannot be null");

            this.categories.add(category);
            return this;
        }

        @Override
        public Builder categories(@NotNull Collection<? extends ConfigCategory> categories) {
            Validate.notNull(categories, "`categories` cannot be null");

            this.categories.addAll(categories);
            return this;
        }

        @Override
        public Builder save(@NotNull Runnable saveFunction) {
            Validate.notNull(saveFunction, "`saveFunction` cannot be null");

            this.saveFunction = saveFunction;
            return this;
        }

        @Override
        public Builder screenInit(@NotNull Consumer<TCLScreen> initConsumer) {
            Validate.notNull(initConsumer, "`initConsumer` cannot be null");

            this.initConsumer = initConsumer;
            return this;
        }

        @Override
        public TheConfigLib build() {
            Validate.notNull(title, "`title must not be null to build `" + TheConfigLib.FULL_NAME + "`");
            Validate.notEmpty(categories, "`categories` must not be empty to build `" + TheConfigLib.FULL_NAME + "`");
            Validate.isTrue(!categories.stream().allMatch(category -> category instanceof PlaceholderCategory), "At least one regular category is required to build `" + TheConfigLib.FULL_NAME + "`");

            return new TheConfigLibImpl(title, ImmutableList.copyOf(categories), saveFunction, initConsumer);
        }
    }
}
