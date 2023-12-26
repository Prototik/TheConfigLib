package dev.tcl.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class RequireRestartScreen extends ConfirmScreen {
    public RequireRestartScreen(@Nullable Screen parent) {
        super(option -> {
            if (option) Minecraft.getInstance().stop();
            else Minecraft.getInstance().setScreen(parent);
        },
                Component.translatable("tcl.restart.title").withStyle(ChatFormatting.RED, ChatFormatting.BOLD),
                Component.translatable("tcl.restart.message"),
                Component.translatable("tcl.restart.yes"),
                Component.translatable("tcl.restart.no")
        );
    }
}
