package dev.tcl.config.impl.autogen;

import dev.tcl.api.Option;
import dev.tcl.api.controller.ControllerBuilder;
import dev.tcl.api.controller.LongFieldControllerBuilder;
import dev.tcl.config.api.ConfigField;
import dev.tcl.config.api.autogen.LongField;
import dev.tcl.config.api.autogen.OptionAccess;
import dev.tcl.config.api.autogen.SimpleOptionFactory;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;

public class LongFieldImpl extends SimpleOptionFactory<LongField, Long> {
    @Override
    protected ControllerBuilder<Long> createController(LongField annotation, ConfigField<Long> field, OptionAccess storage, Option<Long> option) {
        return LongFieldControllerBuilder.create(option)
                .formatValue(v -> {
                    String key = getTranslationKey(field, "fmt." + v);
                    if (Language.getInstance().has(key))
                        return Component.translatable(key);
                    key = getTranslationKey(field, "fmt");
                    if (Language.getInstance().has(key))
                        return Component.translatable(key, v);
                    return Component.literal(Long.toString(v));
                })
                .range(annotation.min(), annotation.max());
    }
}
