package dev.tcl.platform.forge;

import dev.tcl.platform.Env;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class TCLPlatformImpl {
    public static Env getEnvironment() {
        return switch (FMLEnvironment.dist) {
            case CLIENT -> Env.CLIENT;
            case DEDICATED_SERVER -> Env.SERVER;
        };
    }

    public static boolean isDevelopmentEnv() {
        return !FMLEnvironment.production;
    }

    public static Path getConfigDir() {
        return FMLPaths.CONFIGDIR.get();
    }
}
