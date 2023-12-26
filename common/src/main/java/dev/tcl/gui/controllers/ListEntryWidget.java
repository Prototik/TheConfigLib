package dev.tcl.gui.controllers;

import com.google.common.collect.ImmutableList;
import dev.tcl.api.ListOption;
import dev.tcl.api.ListOptionEntry;
import dev.tcl.api.utils.Dimension;
import dev.tcl.gui.AbstractWidget;
import dev.tcl.gui.TooltipButtonWidget;
import dev.tcl.gui.TCLScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ListEntryWidget<T> extends AbstractWidget implements ContainerEventHandler {
    private final TooltipButtonWidget removeButton, moveUpButton, moveDownButton;
    private final AbstractWidget entryWidget;

    private final ListOption<T> listOption;
    private final ListOptionEntry<T> listOptionEntry;

    private final String optionNameString;

    private GuiEventListener focused;
    private boolean dragging;

    public ListEntryWidget(TCLScreen screen, ListOptionEntry<T> listOptionEntry, AbstractWidget entryWidget) {
        super(entryWidget.getDimension().withHeight(Math.max(entryWidget.getDimension().height(), 20) - ((listOptionEntry.parentGroup().indexOf(listOptionEntry) == listOptionEntry.parentGroup().options().size() - 1) ? 0 : 2))); // -2 to remove the padding
        this.listOptionEntry = listOptionEntry;
        this.listOption = listOptionEntry.parentGroup();
        this.optionNameString = listOptionEntry.name().getString().toLowerCase();
        this.entryWidget = entryWidget;

        Dimension<Integer> dim = entryWidget.getDimension();
        entryWidget.setDimension(dim.copy().move(20 * 2, 0).expand(-20 * 3, 0));

        removeButton = new TooltipButtonWidget(screen, dim.xLimit() - 20, dim.y(), 20, 20, Component.literal("❌"), Component.translatable("tcl.list.remove"), btn -> {
            listOption.removeEntry(listOptionEntry);
            updateButtonStates();
        });

        moveUpButton = new TooltipButtonWidget(screen, dim.x(), dim.y(), 20, 20, Component.literal("↑"), Component.translatable("tcl.list.move_up"), btn -> {
            int index = listOption.indexOf(listOptionEntry) - 1;
            if (index >= 0) {
                listOption.removeEntry(listOptionEntry);
                listOption.insertEntry(index, listOptionEntry);
                updateButtonStates();
            }
        });

        moveDownButton = new TooltipButtonWidget(screen, dim.x() + 20, dim.y(), 20, 20, Component.literal("↓"), Component.translatable("tcl.list.move_down"), btn -> {
            int index = listOption.indexOf(listOptionEntry) + 1;
            if (index < listOption.options().size()) {
                listOption.removeEntry(listOptionEntry);
                listOption.insertEntry(index, listOptionEntry);
                updateButtonStates();
            }
        });

        updateButtonStates();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        updateButtonStates(); // update every render in case option becomes available/unavailable

        removeButton.setY(getDimension().y());
        moveUpButton.setY(getDimension().y());
        moveDownButton.setY(getDimension().y());
        entryWidget.setDimension(entryWidget.getDimension().withY(getDimension().y()));

        removeButton.render(graphics, mouseX, mouseY, delta);
        moveUpButton.render(graphics, mouseX, mouseY, delta);
        moveDownButton.render(graphics, mouseX, mouseY, delta);
        entryWidget.render(graphics, mouseX, mouseY, delta);
    }

    protected void updateButtonStates() {
        removeButton.active = listOption.available() && listOption.numberOfEntries() > listOption.minimumNumberOfEntries();
        moveUpButton.active = listOption.indexOf(listOptionEntry) > 0 && listOption.available();
        moveDownButton.active = listOption.indexOf(listOptionEntry) < listOption.options().size() - 1 && listOption.available();
    }

    @Override
    public void unfocus() {
        entryWidget.unfocus();
    }

    @Override
    public void updateNarration(NarrationElementOutput builder) {
        entryWidget.updateNarration(builder);
    }

    @Override
    public boolean matchesSearch(String query) {
        return optionNameString.contains(query.toLowerCase());
    }

    @NotNull
    @Override
    public List<? extends GuiEventListener> children() {
        return ImmutableList.of(moveUpButton, moveDownButton, entryWidget, removeButton);
    }

    @Override
    public boolean isDragging() {
        return dragging;
    }

    @Override
    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    @Nullable
    @Override
    public GuiEventListener getFocused() {
        return focused;
    }

    @Override
    public void setFocused(@Nullable GuiEventListener focused) {
        this.focused = focused;
    }
}
