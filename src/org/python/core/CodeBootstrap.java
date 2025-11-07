package org.python.core;

/**
 * CodeBootstrap
 */
public interface CodeBootstrap {
    
    /**
     * LoadCode
     * 
     * @param loader
     *            loader
     * @return code
     */
    PyCode loadCode(CodeLoader loader);

}
