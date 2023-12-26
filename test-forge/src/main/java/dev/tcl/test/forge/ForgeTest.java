package dev.tcl.test.forge;


import dev.tcl.test.GuiTest;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;

@Mod("tcl_test")
public class ForgeTest {
    public ForgeTest(FMLModContainer modContainer) {
        modContainer.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
                new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> GuiTest.getModConfigScreenFactory(screen)));
    }
}
