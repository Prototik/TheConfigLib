package dev.tcl.config.api.autogen;

import java.util.Optional;

/**
 * Backing interface for the {@link AutoGen} annotation.
 */
public interface AutoGenField {
    String category();

    Optional<String> group();
}
