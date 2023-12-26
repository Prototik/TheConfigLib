package dev.tcl.debug;

import dev.tcl.platform.TCLPlatform;

public final class DebugProperties {
    /** Applies GL filtering to rendering images. */
    public static final boolean IMAGE_FILTERING = boolProp("imageFiltering", false, false);

    private static boolean boolProp(String name, boolean defProd, boolean defDebug) {
        boolean defaultValue = TCLPlatform.isDevelopmentEnv() ? defDebug : defProd;
        return Boolean.parseBoolean(System.getProperty("tcl.debug." + name, Boolean.toString(defaultValue)));
    }
}
