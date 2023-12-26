package dev.tcl.config.impl;

import dev.tcl.api.*;
import dev.tcl.config.api.*;
import dev.tcl.config.api.autogen.AutoGen;
import dev.tcl.config.api.autogen.OptionAccess;
import dev.tcl.config.impl.autogen.OptionAccessImpl;
import dev.tcl.config.impl.autogen.OptionFactoryRegistry;
import dev.tcl.config.impl.autogen.TCLAutoGenException;
import dev.tcl.platform.TCLPlatform;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

public class ConfigClassHandlerImpl<T> implements ConfigClassHandler<T> {
    private final Class<T> configClass;
    private final ResourceLocation id;
    private final boolean supportsAutoGen;
    private final ConfigSerializer<T> serializer;
    private final Collection<? extends ConfigFieldImpl<?>> fields;

    private T instance;
    private final T defaults;
    private final Constructor<T> noArgsConstructor;

    private static final Lock HANDLERS_LOCK = new ReentrantLock();
    private static Map<ConfigClassHandlerImpl<?>, Void> HANDLERS = new WeakHashMap<>();

    @ApiStatus.Internal
    public static void loadAll() {
        HANDLERS_LOCK.lock();
        try {
            var handlers = HANDLERS;
            if (handlers == null) {
                return;
            }
            handlers.keySet().forEach(ConfigClassHandlerImpl::load);
            HANDLERS = null;
        } finally {
            HANDLERS_LOCK.unlock();
        }
    }

    public ConfigClassHandlerImpl(Class<T> configClass, ResourceLocation id, Function<ConfigClassHandler<T>, ConfigSerializer<T>> serializerFactory) {
        this.configClass = configClass;
        this.id = id;
        this.supportsAutoGen = id != null && TCLPlatform.getEnvironment().isClient();

        try {
            noArgsConstructor = configClass.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new TCLAutoGenException("Failed to find no-args constructor for config class %s.".formatted(configClass.getName()), e);
        }
        this.instance = createNewObject();
        this.defaults = createNewObject();
        try {
            this.fields = discoverFields();
        } catch (IllegalAccessException e) {
            throw new TCLAutoGenException(e);
        }
        this.serializer = serializerFactory.apply(this);

        HANDLERS_LOCK.lock();
        try {
            var handlers = HANDLERS;
            if (handlers == null) {
                load();
            } else {
                handlers.put(this, null);
            }
        } finally {
            HANDLERS_LOCK.unlock();
        }
    }

    private Collection<? extends ConfigFieldImpl<?>> discoverFields() throws IllegalAccessException {
        MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(configClass, MethodHandles.lookup());

        return Arrays.stream(configClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(SerialEntry.class) || field.isAnnotationPresent(AutoGen.class))
                .map(field -> {
                    try {
                        VarHandle handle = lookup.unreflectVarHandle(field);
                        FieldAccess<T> fieldAccess = new VarHandleFieldAccess<>(handle, field, instance);
                        ReadOnlyFieldAccess<T> defaultFieldAccess = fieldAccess.forInstance(defaults).asReadOnly();
                        return new ConfigFieldImpl<>(fieldAccess, defaultFieldAccess, this);
                    } catch (IllegalAccessException e) {
                        throw new TCLAutoGenException(e);
                    }
                })
                .toList();
    }

    @Override
    public T instance() {
        return this.instance;
    }

    @Override
    public T defaults() {
        return this.defaults;
    }

    @Override
    public Class<T> configClass() {
        return this.configClass;
    }

    @Override
    public Collection<? extends ConfigFieldImpl<?>> fields() {
        return this.fields;
    }

    @Override
    public @Nullable ResourceLocation id() {
        return this.id;
    }

    @Override
    public boolean supportsAutoGen() {
        return this.supportsAutoGen;
    }

    @Override
    public TheConfigLib generateGui() {
        if (!supportsAutoGen()) {
            throw new TCLAutoGenException("Auto GUI generation is not supported for this config class. You either need to enable it in the builder or you are attempting to create a GUI in a dedicated server environment.");
        }

        boolean hasAutoGenFields = fields().stream().anyMatch(field -> field.autoGen().isPresent());

        if (!hasAutoGenFields) {
            throw new TCLAutoGenException("No fields in this config class are annotated with @AutoGen. You must annotate at least one field with @AutoGen to generate a GUI.");
        }

        OptionAccessImpl storage = new OptionAccessImpl();
        Map<String, CategoryAndGroups> categories = new LinkedHashMap<>();
        for (ConfigField<?> configField : fields()) {
            configField.autoGen().ifPresent(autoGen -> {
                CategoryAndGroups groups = categories.computeIfAbsent(
                        autoGen.category(),
                        k -> new CategoryAndGroups(
                                ConfigCategory.createBuilder()
                                        .name(Component.translatable("tcl.config.%s.category.%s".formatted(id().toLanguageKey(), k))),
                                new LinkedHashMap<>()
                        )
                );
                OptionAddable group = groups.groups().computeIfAbsent(autoGen.group().orElse(""), k -> {
                    if (k.isEmpty())
                        return groups.category();
                    return OptionGroup.createBuilder()
                            .name(Component.translatable("tcl.config.%s.category.%s.group.%s".formatted(id().toLanguageKey(), autoGen.category(), k)));
                });

                Option<?> option;
                try {
                    option = createOption(configField, storage);
                } catch (Exception e) {
                    throw new TCLAutoGenException("Failed to create option for field '%s'".formatted(configField.access().name()), e);
                }

                storage.putOption(configField.access().name(), option);
                group.option(option);
            });
        }
        storage.checkBadOperations();
        categories.values().forEach(CategoryAndGroups::finaliseGroups);

        TheConfigLib.Builder tclBuilder = TheConfigLib.createBuilder()
                .save(this::save)
                .title(Component.translatable("tcl.config.%s.title".formatted(this.id().toLanguageKey())));
        categories.values().forEach(category -> tclBuilder.category(category.category().build()));

        return tclBuilder.build();
    }

    private <U> Option<U> createOption(ConfigField<U> configField, OptionAccess storage) {
        return OptionFactoryRegistry.createOption(configField, storage)
                .orElseThrow(() -> new TCLAutoGenException("Failed to create option for field %s".formatted(configField.access().name())));
    }

    @Override
    public boolean load() {
        // create a new instance to load into
        T newInstance = createNewObject();

        // attempt to load the config
        ConfigSerializer.LoadResult loadResult = ConfigSerializer.LoadResult.FAILURE;
        Throwable error = null;
        try {
            loadResult = serializer.loadSafely(newInstance);
        } catch (Throwable e) {
            // handle any errors later in the loadResult switch case
            error = e;
        }

        switch (loadResult) {
            case DIRTY:
            case SUCCESS:
                // replace the instance with the newly created one
                this.instance = newInstance;
                for (ConfigFieldImpl<?> field : fields()) {
                    // update the field accesses to point to the correct object
                    field.trackNewInstance(newInstance);
                }

                if (loadResult == ConfigSerializer.LoadResult.DIRTY) {
                    // if the load result is dirty, we need to save the config again
                    this.save();
                }
            case NO_CHANGE:
                return true;
            case FAILURE:
                TheConfigLib.LOGGER.error(
                        "Unsuccessful load of config class '{}'. The load will be abandoned and config remains unchanged.",
                        configClass.getSimpleName(), error
                );
        }

        return false;
    }

    @Override
    public void save() {
        serializer.save();
    }

    private T createNewObject() {
        try {
            return noArgsConstructor.newInstance();
        } catch (Exception e) {
            throw new TCLAutoGenException("Failed to create instance of config class '%s' with no-args constructor.".formatted(configClass.getName()), e);
        }
    }

    public static class BuilderImpl<T> implements Builder<T> {
        private final Class<T> configClass;
        private ResourceLocation id;
        private Function<ConfigClassHandler<T>, ConfigSerializer<T>> serializerFactory;

        public BuilderImpl(Class<T> configClass) {
            this.configClass = configClass;
        }

        @Override
        public Builder<T> id(ResourceLocation id) {
            this.id = id;
            return this;
        }

        @Override
        public Builder<T> serializer(Function<ConfigClassHandler<T>, ConfigSerializer<T>> serializerFactory) {
            this.serializerFactory = serializerFactory;
            return this;
        }

        @Override
        public ConfigClassHandler<T> build() {
            Validate.notNull(serializerFactory, "serializerFactory must not be null");
            Validate.notNull(configClass, "configClass must not be null");

            return new ConfigClassHandlerImpl<>(configClass, id, serializerFactory);
        }
    }

    private record CategoryAndGroups(ConfigCategory.Builder category, Map<String, OptionAddable> groups) {
        private void finaliseGroups() {
            groups.forEach((name, group) -> {
                if (group instanceof OptionGroup.Builder groupBuilder) {
                    category.group(groupBuilder.build());
                }
            });
        }
    }
}
