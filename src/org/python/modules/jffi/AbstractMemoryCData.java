
package org.python.modules.jffi;

import org.python.core.PyType;

/**
 * AbstractMemoryCData
 */
public abstract class AbstractMemoryCData extends CData implements Pointer {
    /** memory */
    protected DirectMemory memory;

    /**
     * Constructor
     * 
     * @param subtype
     *            subtype
     * @param type
     *            type
     * @param memory
     *            memory
     */
    AbstractMemoryCData(PyType subtype, CType type, DirectMemory memory) {
        super(subtype, type);
        this.memory = memory;
    }

    @Override
    public boolean __nonzero__() {
        return !getMemory().isNull();
    }

    protected void initReferenceMemory(Memory m) {
        m.putAddress(0, memory);
    }

    public final DirectMemory getMemory() {
        return hasReferenceMemory() ? getReferenceMemory().getMemory(0) : memory;
    }
}
