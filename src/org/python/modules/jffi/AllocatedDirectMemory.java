
package org.python.modules.jffi;

public interface AllocatedDirectMemory extends DirectMemory, AutoCloseable {
    public void free();
    public void setAutoRelease(boolean autorelease);
}
