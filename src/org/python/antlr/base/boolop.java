// Hand copied from stmt.
// XXX: autogenerate this.
package org.python.antlr.base;

import org.antlr.runtime.Token;
import org.python.antlr.AST;
import org.python.antlr.PythonTree;
import org.python.core.PyString;
import org.python.core.PyType;
import org.python.expose.ExposedGet;
import org.python.expose.ExposedType;

/**
 * boolop
 */
@ExposedType(name = "_ast.boolop", base = AST.class)
public abstract class boolop extends PythonTree {

    /** TYPE */
    public static final PyType TYPE = PyType.fromClass(boolop.class);
    private final static PyString[] fields = new PyString[0];

    /**
     * Get_fields
     * 
     * @return fields
     */
    @ExposedGet(name = "_fields")
    public PyString[] get_fields() { return fields; }

    private final static PyString[] attributes =
    new PyString[] {new PyString("lineno"), new PyString("col_offset")};

    /**
     * Get_attributes
     * 
     * @return attributes
     */
    @ExposedGet(name = "_attributes")
    public PyString[] get_attributes() { return attributes; }

    /** Default constructor */
    public boolop() {
    }

    /**
     * Constructor
     * 
     * @param subType
     *            subType
     */
    public boolop(PyType subType) {
    }

    /**
     * Constructor
     * 
     * @param ttype
     *            ttype
     * @param token
     *            token
     */
    public boolop(int ttype, Token token) {
        super(ttype, token);
    }

    /**
     * Constructor
     * 
     * @param token
     *            token
     */
    public boolop(Token token) {
        super(token);
    }

    /**
     * Constructor
     * 
     * @param node
     *            node
     */
    public boolop(PythonTree node) {
        super(node);
    }

}
