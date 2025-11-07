package org.python.antlr;

import org.python.core.Py;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.core.PyType;
import org.python.core.Untraversable;
import org.python.expose.ExposedType;

/**
 * AST
 */
@Untraversable
@ExposedType(name = "_ast.AST", base = PyObject.class)
public class AST extends PyObject {
    /** TYPE */
    public static final PyType TYPE = PyType.fromClass(AST.class);
    
    /**
     * Default constructor
     */
    public AST() {
    }

    /**
     * Constructor
     * 
     * @param objtype
     *            objtype
     */
    public AST(PyType objtype) {
        super(objtype);
    }

    /**
     * Check
     * 
     * @param nargs
     *            nargs
     * @param expected
     *            expected
     * @param takesZeroArgs
     *            takesZeroArgs
     * @return check
     */
    public static boolean check(int nargs, int expected, boolean takesZeroArgs) {
        if (nargs == expected) {
            return true;
        }
        if (takesZeroArgs && nargs == 0) {
            return true;
        }
        return false;
    }

    /**
     * UnexpectedCall
     * 
     * @param expected
     *            expected
     * @param name
     *            name
     * @return exc
     */
    public static PyException unexpectedCall(int expected, String name) {
        String message = " constructor takes 0 positional arguments";
        if (expected != 0) {
            message = " constructor takes either 0 or " + expected + " arguments";
        }
        return Py.TypeError(name + message);
    }
}
