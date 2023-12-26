package dev.tcl.platform.forge;

import dev.tcl.api.TheConfigLib;
import dev.tcl.config.impl.ConfigClassHandlerImpl;
import dev.tcl.gui.image.TCLImageReloadListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(TheConfigLib.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TCLForgeEntrypoint {
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onRegisterClientReloadListenersEvent(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new TCLImageReloadListener());
    }

    @SubscribeEvent
    public static void onSetup(FMLCommonSetupEvent event) {
        ConfigClassHandlerImpl.loadAll();
    }
}
