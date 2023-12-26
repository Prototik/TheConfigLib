package dev.tcl.config.api;

import dev.tcl.api.TheConfigLib;
import dev.tcl.config.api.autogen.AutoGen;
import dev.tcl.config.api.serializer.GsonConfigSerializerBuilder;
import dev.tcl.config.impl.ConfigClassHandlerImpl;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.function.Function;

/**
 * Represents a handled config class.
 *
 * @param <T> the backing config class to be managed
 */
public interface ConfigClassHandler<T> {
    /**
     * Gets the working instance of the config class.
     * This should be used to get and set fields like usual.
     */
    T instance();

    /**
     * Gets a second instance of the config class that
     * should be used to get default values only. No fields
     * should be modified in this instance.
     */
    T defaults();

    /**
     * Gets the class of the config.
     */
    Class<T> configClass();

    /**
     * Get all eligible fields in the config class.
     * They could either be annotated with {@link AutoGen}
     * or {@link SerialEntry}, do not assume that a field has both of these.
     */
    Collection<? extends ConfigField<?>> fields();

    /**
     * The unique identifier of this config handler.
     */
    @Nullable
    ResourceLocation id();

    /**
     * Auto-generates a GUI for this config class.
     * This throws an exception if auto-gen is not supported.
     */
    TheConfigLib generateGui();

    /**
     * Whether this config class supports auto-gen.
     * If on a dedicated server, this returns false.
     */
    boolean supportsAutoGen();

    /**
     * Safely loads the config class using the provided serializer.
     * @return if the config was loaded successfully
     */
    boolean load();

    /**
     * Safely saves the config class using the provided serializer.
     */
    void save();

    /**
     * Creates a builder for a config class.
     *
     * @param configClass the config class to build
     * @param <T> the type of the config class
     * @return the builder
     */
    static <T> Builder<T> createBuilder(Class<T> configClass) {
        return new ConfigClassHandlerImpl.BuilderImpl<>(configClass);
    }

    static <T> ConfigClassHandler<T> json(ResourceLocation id, Class<T> configClass) {
        return createBuilder(configClass)
                .id(id)
                .serializer(config -> GsonConfigSerializerBuilder.create(config).build())
                .build();
    }

    @SafeVarargs
    static <T> ConfigClassHandler<T> json(ResourceLocation id, T... configClass) {
        if (configClass.length != 0) throw new IllegalStateException("array must be empty!");
        //noinspection unchecked
        return json(id, (Class<T>) configClass.getClass().getComponentType());
    }

    static <T> ConfigClassHandler<T> json5(ResourceLocation id, Class<T> configClass) {
        return createBuilder(configClass)
                .id(id)
                .serializer(config -> GsonConfigSerializerBuilder.create(config).setJson5(true).build())
                .build();
    }

    @SafeVarargs
    static <T> ConfigClassHandler<T> json5(ResourceLocation id, T... configClass) {
        if (configClass.length != 0) throw new IllegalStateException("array must be empty!");
        //noinspection unchecked
        return json5(id, (Class<T>) configClass.getClass().getComponentType());
    }

    interface Builder<T> {
        /**
         * The unique identifier of this config handler.
         * The namespace should be your modid.
         *
         * @return this builder
         */
        Builder<T> id(ResourceLocation id);

        /**
         * The function to create the serializer for this config class.
         *
         * @return this builder
         */
        Builder<T> serializer(Function<ConfigClassHandler<T>, ConfigSerializer<T>> serializerFactory);

        ConfigClassHandler<T> build();
    }
}
