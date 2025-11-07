/* Copyright (c) 2012 Jython Developers */
package org.python.modules.itertools;

import org.python.core.ArgParser;
import org.python.core.PyIterator;
import org.python.core.PyObject;
import org.python.core.PyTuple;
import org.python.core.PyType;
import org.python.core.Visitproc;
import org.python.expose.ExposedClassMethod;
import org.python.expose.ExposedMethod;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

/**
 * chain
 */
@ExposedType(name = "itertools.chain", base = PyObject.class, doc = chain.chain_doc)
public class chain extends PyIterator {

    /** TYPE */
    public static final PyType TYPE = PyType.fromClass(chain.class);
    private itertools.ItertoolsIterator iter;

    /** chain_doc */
    public static final String chain_doc =
        "chain(*iterables) --> chain object\n\n" +
        "Return a chain object whose .next() method returns elements from the\n" +
        "first iterable until it is exhausted, then elements from the next\n" +
        "iterable, until all of the iterables are exhausted.";

    /** Default constructor */
    public chain() {
        super();
    }

    /**
     * Constructor
     * 
     * @param subType
     *            subType
     */
    public chain(PyType subType) {
        super(subType);
    }

    /**
     * Constructor
     * 
     * @param iterable
     *            iterable
     */
    public chain(PyObject iterable) {
        super();
        chain___init__(iterable.__iter__());
    }

    /**
     * From_iterable
     * 
     * @param type
     *            type
     * @param iterable
     *            iterable
     * @return o
     */
    @ExposedClassMethod
    public static final PyObject from_iterable(PyType type, PyObject iterable) {
        return new chain(iterable);
    }

    /**
     * Creates an iterator that iterates over a <i>chain</i> of iterables.
     */
    @ExposedNew
    @ExposedMethod
    final void chain___init__(final PyObject[] args, String[] kwds) {
        ArgParser ap = new ArgParser("chain", args, kwds, "iterables");
        ap.noKeywords();

        //ArgParser always returns a PyTuple - I wonder why we make it pass back a PyObject?
        PyTuple tuple = (PyTuple)ap.getList(0);
        chain___init__(tuple.__iter__());
    }

    private void chain___init__(final PyObject superIterator) {

        iter = new itertools.ItertoolsIterator() {

            int iteratorIndex = 0;
            PyObject currentIterator = new PyObject();

            public PyObject __iternext__() {
                PyObject nextIterable;
                PyObject next = null;
                do {
                    next = nextElement(currentIterator);
                    if (next != null) {
                        break;
                    }

                }
                while ((nextIterable = nextElement(superIterator)) != null &&
                       (currentIterator = nextIterable.__iter__()) != null);
                return next;
            }

        };
    }

    public PyObject __iternext__() {
        return iter.__iternext__();
    }

    @ExposedMethod
    @Override
    public PyObject next() {
        return doNext(__iternext__());
    }


    /* Traverseproc implementation */
    @Override
    public int traverse(Visitproc visit, Object arg) {
        int retVal = super.traverse(visit, arg);
        if (retVal != 0) {
            return retVal;
        }
        return iter != null ? visit.visit(iter, arg) : 0;
    }

    @Override
    public boolean refersDirectlyTo(PyObject ob) {
        return ob != null && (iter == ob || super.refersDirectlyTo(ob));
    }
}
