package dev.tcl.config.impl.autogen;

import dev.tcl.api.Option;
import dev.tcl.api.TheConfigLib;
import dev.tcl.config.api.ConfigField;
import dev.tcl.config.api.autogen.*;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OptionFactoryRegistry {
    private static final Map<Class<?>, OptionFactory<?, ?>> factoryMap = new HashMap<>();

    static {
        registerOptionFactory(TickBox.class, new TickBoxImpl());
        registerOptionFactory(Bool.class, new BoolImpl());
        registerOptionFactory(IntSlider.class, new IntSliderImpl());
        registerOptionFactory(LongSlider.class, new LongSliderImpl());
        registerOptionFactory(FloatSlider.class, new FloatSliderImpl());
        registerOptionFactory(DoubleSlider.class, new DoubleSliderImpl());
        registerOptionFactory(IntField.class, new IntFieldImpl());
        registerOptionFactory(LongField.class, new LongFieldImpl());
        registerOptionFactory(FloatField.class, new FloatFieldImpl());
        registerOptionFactory(DoubleField.class, new DoubleFieldImpl());
        registerOptionFactory(EnumCycler.class, new EnumCyclerImpl<>());
        registerOptionFactory(EnumDropdown.class, new EnumDropdownImpl<>());
        registerOptionFactory(StringField.class, new StringFieldImpl());
        registerOptionFactory(ColorField.class, new ColorFieldImpl());
        registerOptionFactory(Dropdown.class, new DropdownImpl());
        registerOptionFactory(ItemField.class, new ItemFieldImpl());
        registerOptionFactory(Label.class, new LabelImpl());
        registerOptionFactory(ListGroup.class, new ListGroupImpl<>());

        registerOptionFactory(MasterTickBox.class, new MasterTickBoxImpl());
    }

    public static <A extends Annotation, T> void registerOptionFactory(Class<A> annotation, OptionFactory<? extends A, ? super T> factory) {
        factoryMap.put(annotation, factory);
    }

    public static <T> Optional<Option<T>> createOption(ConfigField<T> configField, OptionAccess storage) {
        Annotation[] annotations = configField.access().getAnnotations().stream()
                .filter(annotation -> factoryMap.containsKey(annotation.annotationType()))
                .toArray(Annotation[]::new);

        if (annotations.length != 1) {
            TheConfigLib.LOGGER.warn("Found {} option factory annotations on field {}, expected 1", annotations.length, configField.access().name());

            if (annotations.length == 0) {
                return Optional.empty();
            }
        }

        Annotation annotation = annotations[0];
        // noinspection unchecked
        OptionFactory<Annotation, T> factory = (OptionFactory<Annotation, T>) factoryMap.get(annotation.annotationType());
        return Optional.of(factory.createOption(annotation, configField, storage));
    }
}
