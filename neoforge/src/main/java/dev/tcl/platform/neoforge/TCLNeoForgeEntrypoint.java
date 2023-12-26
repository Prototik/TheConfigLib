package dev.tcl.platform.neoforge;

import dev.tcl.api.TheConfigLib;
import dev.tcl.config.impl.ConfigClassHandlerImpl;
import dev.tcl.gui.image.TCLImageReloadListener;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;

@Mod(TheConfigLib.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TCLNeoForgeEntrypoint {
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onRegisterClientReloadListenersEvent(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new TCLImageReloadListener());
    }

    @SubscribeEvent
    public static void onSetup(FMLCommonSetupEvent event) {
        ConfigClassHandlerImpl.loadAll();
    }
}
