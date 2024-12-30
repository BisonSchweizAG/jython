package org.python.core;

import java.lang.ref.Cleaner;

/**
 * A wrapper around the singleton {@code Cleaner} instance
 */
public enum PyCleaner {
    INSTANCE;

    private final Cleaner cleaner;

    private PyCleaner() {
        cleaner = Cleaner.create();
    }

    /**
     * Get the singleton {@code Cleaner} instance
     * 
     * @return the cleaner
     */
    public Cleaner get() {
        return cleaner;
    }
}
