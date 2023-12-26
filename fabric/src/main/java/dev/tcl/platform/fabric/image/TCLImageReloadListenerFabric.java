package dev.tcl.platform.fabric.image;

import dev.tcl.api.TheConfigLib;
import dev.tcl.gui.image.TCLImageReloadListener;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class TCLImageReloadListenerFabric extends TCLImageReloadListener implements IdentifiableResourceReloadListener {
    @Override
    public ResourceLocation getFabricId() {
        return new ResourceLocation(TheConfigLib.MOD_ID, "image_reload_listener");
    }
}
