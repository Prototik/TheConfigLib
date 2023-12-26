package dev.tcl.config.api;

import java.util.Optional;

/**
 * The backing interface for the {@link SerialEntry} annotation.
 */
public interface SerialField {
    String serialName();

    Optional<String> comment();

    boolean required();

    boolean nullable();
}
