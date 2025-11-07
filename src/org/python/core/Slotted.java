package org.python.core;

/**
 * Slotted
 */
public interface Slotted {

    /**
     * GetSlot
     * 
     * @param index
     *            index
     * @return o
     */
    public PyObject getSlot(int index);

    /**
     * SetSlot
     * 
     * @param index
     *            index
     * @param value
     *            value
     */
    public void setSlot(int index, PyObject value);
}
