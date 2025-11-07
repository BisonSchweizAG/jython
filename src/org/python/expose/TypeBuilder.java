package org.python.expose;

import org.python.core.PyObject;
import org.python.core.PyType;

/**
 * Contains the basic information needed to construct a builtin Python type.
 */
public interface TypeBuilder {

    /**
     * GetName
     * 
     * @return s
     */
    public String getName();

    /**
     * GetDict
     * 
     * @param type
     *            type
     * @return o
     */
    public PyObject getDict(PyType type);

    /**
     * GetTypeClass
     * 
     * @return class
     */
    public Class<? extends PyObject> getTypeClass();

    /**
     * GetBase
     * 
     * @return class
     */
    public Class<?> getBase();

    /**
     * GetIsBaseType
     * 
     * @return isBasetype
     */
    public boolean getIsBaseType();

    /**
     * GetDoc
     * 
     * @return s
     */
    public String getDoc();
}
