package dev.tcl.platform.fabric;

import dev.tcl.platform.fabric.image.TCLImageReloadListenerFabric;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

public class TCLClientFabricEntrypoint implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new TCLImageReloadListenerFabric());
    }
}
