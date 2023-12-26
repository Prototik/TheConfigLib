package dev.tcl.impl;

import com.google.common.collect.ImmutableSet;
import dev.tcl.api.*;
import dev.tcl.gui.controllers.LabelController;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;

@ApiStatus.Internal
public final class LabelOptionImpl implements LabelOption {
    private final Component label;
    private final Component name = Component.literal("Label Option");
    private final OptionDescription description;
    private final LabelController labelController;
    private final Binding<Component> binding;

    public LabelOptionImpl(Component label) {
        Validate.notNull(label, "`label` must not be null");

        this.label = label;
        this.labelController = new LabelController(this);
        this.binding = Binding.immutable(label);
        this.description = OptionDescription.createBuilder()
                .text(this.label)
                .build();
    }

    @Override
    public @NotNull Component label() {
        return label;
    }

    @Override
    public @NotNull Component name() {
        return name;
    }

    @Override
    public @NotNull OptionDescription description() {
        return description;
    }

    @Override
    public @NotNull Controller<Component> controller() {
        return labelController;
    }

    @Override
    public @NotNull Binding<Component> binding() {
        return binding;
    }

    @Override
    public boolean available() {
        return true;
    }

    @Override
    public void setAvailable(boolean available) {
        throw new UnsupportedOperationException("Label options cannot be disabled.");
    }

    @Override
    public @NotNull ImmutableSet<OptionFlag> flags() {
        return ImmutableSet.of();
    }

    @Override
    public boolean changed() {
        return false;
    }

    @Override
    public @NotNull Component pendingValue() {
        return label;
    }

    @Override
    public void requestSet(@NotNull Component value) {

    }

    @Override
    public boolean applyValue() {
        return false;
    }

    @Override
    public void forgetPendingValue() {

    }

    @Override
    public void requestSetDefault() {

    }

    @Override
    public boolean isPendingValueDefault() {
        return true;
    }

    @Override
    public boolean canResetToDefault() {
        return false;
    }

    @Override
    public void addListener(BiConsumer<Option<Component>, Component> changedListener) {

    }

    @ApiStatus.Internal
    public static final class BuilderImpl implements Builder {
        private final List<Component> lines = new ArrayList<>();

        @Override
        public Builder line(@NotNull Component line) {
            Validate.notNull(line, "`line` must not be null");

            this.lines.add(line);
            return this;
        }

        @Override
        public Builder lines(@NotNull Collection<? extends Component> lines) {
            this.lines.addAll(lines);
            return this;
        }

        @Override
        public LabelOption build() {
            MutableComponent text = Component.empty();
            Iterator<Component> iterator = lines.iterator();
            while (iterator.hasNext()) {
                text.append(iterator.next());

                if (iterator.hasNext())
                    text.append("\n");
            }

            return new LabelOptionImpl(text);
        }
    }
}
