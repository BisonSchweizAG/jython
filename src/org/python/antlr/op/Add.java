package org.python.antlr.op;

import org.python.antlr.PythonTree;
import org.python.antlr.base.operator;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyType;
import org.python.expose.ExposedGet;
import org.python.expose.ExposedMethod;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

/**
 * Add
 */
@ExposedType(name = "_ast.Add", base = operator.class)
public class Add extends PythonTree {
    /** TYPE */
    public static final PyType TYPE = PyType.fromClass(Add.class);

    /** Default constructor */
    public Add() {
    }

    /**
     * Consructor
     * 
     * @param subType
     *            subType
     */
    public Add(PyType subType) {
        super(subType);
    }

    /**
     * Add__init__
     * 
     * @param args
     *            args
     * @param keywords
     *            keywords
     */
    @ExposedNew
    @ExposedMethod
    public void Add___init__(PyObject[] args, String[] keywords) {
    }

    /** fields */
    private final static PyString[] fields = new PyString[0];

    /**
     * Get_fields
     * 
     * @return fields
     */
    @ExposedGet(name = "_fields")
    public PyString[] get_fields() {
        return fields;
    }

    private final static PyString[] attributes = new PyString[0];

    /**
     * Get_attributes
     * 
     * @return attributes
     */
    @ExposedGet(name = "_attributes")
    public PyString[] get_attributes() {
        return attributes;
    }

    /**
     * __int__
     */
    @ExposedMethod
    public PyObject __int__() {
        return Add___int__();
    }

    /**
     * ADD__int__
     * 
     * @return o
     */
    final PyObject Add___int__() {
        return Py.newInteger(1);
    }

}
