package dev.tcl.test.neoforge;

import dev.tcl.test.GuiTest;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import net.neoforged.neoforge.client.ConfigScreenHandler;

@Mod("tcl_test")
public class NeoForgeTest {
    public NeoForgeTest(FMLModContainer modContainer) {
        modContainer.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
                new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> GuiTest.getModConfigScreenFactory(screen)));
    }
}
