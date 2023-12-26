package dev.tcl.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import org.jetbrains.annotations.Contract;

import java.nio.file.Path;

@SuppressWarnings("Contract")
public final class TCLPlatform {
    @Contract(value = "-> _", pure = true)
    @ExpectPlatform
    public static Env getEnvironment() {
        throw new AssertionError();
    }

    @Contract(value = "-> _", pure = true)
    @ExpectPlatform
    public static Path getConfigDir() {
        throw new AssertionError();
    }

    @Contract(value = "-> _", pure = true)
    @ExpectPlatform
    public static boolean isDevelopmentEnv() {
        throw new AssertionError();
    }
}
