package dev.tcl.config.api.autogen;

public interface CyclableEnum<T extends Enum<T>> {
    T[] allowedValues();
}
