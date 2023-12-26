package dev.tcl.platform.fabric;

import dev.tcl.config.impl.ConfigClassHandlerImpl;
import net.fabricmc.api.ModInitializer;

public class TCLFabricEntrypoint implements ModInitializer {
    @Override
    public void onInitialize() {
        ConfigClassHandlerImpl.loadAll();
    }
}
