package org.python.core;

/**
 * Visitproc
 */
public interface Visitproc {

    /**
     * Must not be called with {@code object == null}.
     * 
     * @param object
     *            object
     * @param arg
     *            arg
     * @return i
     */
    public int visit(PyObject object, Object arg);
}
