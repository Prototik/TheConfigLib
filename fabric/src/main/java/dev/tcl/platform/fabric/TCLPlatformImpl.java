package dev.tcl.platform.fabric;

import dev.tcl.platform.Env;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class TCLPlatformImpl {
    public static Env getEnvironment() {
        return switch (FabricLoader.getInstance().getEnvironmentType()) {
            case CLIENT -> Env.CLIENT;
            case SERVER -> Env.SERVER;
        };
    }

    public static boolean isDevelopmentEnv() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    public static Path getConfigDir() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
