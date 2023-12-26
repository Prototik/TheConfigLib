package dev.tcl.mixin;

import dev.tcl.gui.image.ImageRendererManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "destroy", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;close()V", shift = At.Shift.BEFORE))
    private void closeImages(CallbackInfo ci) {
        ImageRendererManager.closeAll();
    }
}
