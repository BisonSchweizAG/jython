
package org.python.compiler;

import org.python.antlr.PythonTree;

/**
 * CompilationContext
 */
public interface CompilationContext {

    /**
     * GetFutures
     * 
     * @return future
     */
    public Future getFutures();

    /**
     * Error
     * 
     * @param msg
     *            msg
     * @param err
     *            err
     * @param node
     *            node
     * @throws Exception
     *             exception
     */
    public void error(String msg,boolean err,PythonTree node)
        throws Exception;

    /**
     * GetFilename
     * 
     * @return s
     */
    public String getFilename();

    /**
     * GetScopeInfo
     * 
     * @param node
     *            node
     * @return info
     */
    public ScopeInfo getScopeInfo(PythonTree node);
}
