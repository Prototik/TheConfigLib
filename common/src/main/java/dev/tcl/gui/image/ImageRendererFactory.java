package dev.tcl.gui.image;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface ImageRendererFactory {
    /**
     * Prepares the image. This can be run off-thread,
     * and should NOT contain any GL calls whatsoever.
     */
    ImageSupplier prepareImage() throws Exception;

    default boolean requiresOffThreadPreparation() {
        return true;
    }

    interface ImageSupplier {
        ImageRenderer completeImage() throws Exception;
    }

    interface OnThread extends ImageRendererFactory {
        @Override
        default boolean requiresOffThreadPreparation() {
            return false;
        }
    }
}
