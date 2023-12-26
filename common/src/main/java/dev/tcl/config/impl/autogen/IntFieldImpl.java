package dev.tcl.config.impl.autogen;

import dev.tcl.api.Option;
import dev.tcl.api.controller.ControllerBuilder;
import dev.tcl.api.controller.IntegerFieldControllerBuilder;
import dev.tcl.config.api.ConfigField;
import dev.tcl.config.api.autogen.IntField;
import dev.tcl.config.api.autogen.OptionAccess;
import dev.tcl.config.api.autogen.SimpleOptionFactory;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;

public class IntFieldImpl extends SimpleOptionFactory<IntField, Integer> {
    @Override
    protected ControllerBuilder<Integer> createController(IntField annotation, ConfigField<Integer> field, OptionAccess storage, Option<Integer> option) {
        return IntegerFieldControllerBuilder.create(option)
                .formatValue(v -> {
                    String key = getTranslationKey(field, "fmt." + v);
                    if (Language.getInstance().has(key))
                        return Component.translatable(key);
                    key = getTranslationKey(field, "fmt");
                    if (Language.getInstance().has(key))
                        return Component.translatable(key, v);
                    return Component.literal(Integer.toString(v));
                })
                .range(annotation.min(), annotation.max());
    }
}
