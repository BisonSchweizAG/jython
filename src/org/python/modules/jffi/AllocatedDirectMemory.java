
package org.python.modules.jffi;

/**
 * AllocatedDirectMemory
 */
public interface AllocatedDirectMemory extends DirectMemory {
    /**
     * Free
     */
    public void free();

    /**
     * SetAutoRelease
     * 
     * @param autorelease
     *            autorelease
     */
    public void setAutoRelease(boolean autorelease);
}
