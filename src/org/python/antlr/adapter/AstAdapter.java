package org.python.antlr.adapter;

import java.util.List;

import org.python.core.PyObject;

/**
 * AstAdapters turn Objects into Ast nodes.
 */
public interface AstAdapter {

    /**
     * ast2py
     * 
     * @param o
     *            o
     * @return o
     */
	PyObject ast2py(Object o);

    /**
     * py2ast
     * 
     * @param o
     *            o
     * @return o
     */
	Object py2ast(PyObject o);

    /**
     * iter2ast
     * 
     * @param o
     *            o
     * @return l
     */
	List iter2ast(PyObject o);

}
