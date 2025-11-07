package org.python.core;

/** A <code>PyObject</code> that provides <code>__enter__</code> and <code>__exit__</code> methods for use in the with-statement.
 *
 * Implementing context managers can then be potentially inlined by the JVM.
 */
public interface ContextManager {
    /**
     * __enter__
     * 
     * @param ts
     *            ts
     * @return o
     */
    public PyObject __enter__(ThreadState ts);

    /**
     * __exit__
     * 
     * @param ts
     *            tx
     * @param exception
     *            exception
     * @return exit
     */
    public boolean __exit__(ThreadState ts, PyException exception);
}
