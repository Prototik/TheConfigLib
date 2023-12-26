package dev.tcl.config.impl.autogen;

public class TCLAutoGenException extends RuntimeException {
    public TCLAutoGenException(String message) {
        super(message);
    }

    public TCLAutoGenException(String message, Throwable e) {
        super(message, e);
    }

    public TCLAutoGenException(Throwable e) {
        super(e);
    }
}
