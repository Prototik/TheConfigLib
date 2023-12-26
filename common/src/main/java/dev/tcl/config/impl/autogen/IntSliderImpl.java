package dev.tcl.config.impl.autogen;

import dev.tcl.api.Option;
import dev.tcl.api.controller.ControllerBuilder;
import dev.tcl.api.controller.IntegerSliderControllerBuilder;
import dev.tcl.config.api.ConfigField;
import dev.tcl.config.api.autogen.SimpleOptionFactory;
import dev.tcl.config.api.autogen.IntSlider;
import dev.tcl.config.api.autogen.OptionAccess;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;

public class IntSliderImpl extends SimpleOptionFactory<IntSlider, Integer> {
    @Override
    protected ControllerBuilder<Integer> createController(IntSlider annotation, ConfigField<Integer> field, OptionAccess storage, Option<Integer> option) {
        return IntegerSliderControllerBuilder.create(option)
                .formatValue(v -> {
                    String key = getTranslationKey(field, "fmt." + v);
                    if (Language.getInstance().has(key))
                        return Component.translatable(key);
                    key = getTranslationKey(field, "fmt");
                    if (Language.getInstance().has(key))
                        return Component.translatable(key, v);
                    return Component.literal(Integer.toString(v));
                })
                .range(annotation.min(), annotation.max())
                .step(annotation.step());
    }
}
