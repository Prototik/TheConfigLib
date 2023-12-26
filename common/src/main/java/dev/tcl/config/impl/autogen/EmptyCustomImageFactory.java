package dev.tcl.config.impl.autogen;

import dev.tcl.config.api.ConfigField;
import dev.tcl.config.api.autogen.OptionAccess;
import dev.tcl.config.api.autogen.CustomImage;
import dev.tcl.gui.image.ImageRenderer;

import java.util.concurrent.CompletableFuture;

public class EmptyCustomImageFactory implements CustomImage.CustomImageFactory<Object> {

    @Override
    public CompletableFuture<ImageRenderer> createImage(Object value, ConfigField<Object> field, OptionAccess access) {
        throw new IllegalStateException();
    }
}
