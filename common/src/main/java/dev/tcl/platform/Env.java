package dev.tcl.platform;

public enum Env {
    CLIENT,
    SERVER;

    public boolean isClient() {
        return this == Env.CLIENT;
    }
}
