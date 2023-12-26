package dev.tcl.config.api;

/**
 * The base class for config serializers,
 * offering a method to save and load.
 * @param <T> the config class to be (de)serialized
 */
public abstract class ConfigSerializer<T> {
    protected final ConfigClassHandler<T> config;

    public ConfigSerializer(ConfigClassHandler<T> config) {
        this.config = config;
    }

    /**
     * Saves all fields in the config class.
     * This can be done any way as it's abstract, but most
     * commonly it is saved to a file.
     */
    public abstract void save();

    /**
     * Loads all fields into the config class.
     *
     * @param newInstance@return the result of the load
     */
    public LoadResult loadSafely(T newInstance) {
        return LoadResult.NO_CHANGE;
    }

    public enum LoadResult {
        /**
         * Indicates that the config was loaded successfully and the temporary object should be applied.
         */
        SUCCESS,
        /**
         * Indicates that the config was not loaded successfully and the load should be abandoned.
         */
        FAILURE,
        /**
         * Indicates that the config has not changed after a load and the temporary object should be ignored.
         */
        NO_CHANGE,
        /**
         * Indicates the config was loaded successfully, but the config should be re-saved straight away.
         */
        DIRTY
    }
}
