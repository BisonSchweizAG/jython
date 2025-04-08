
package org.python.modules.jffi;

import java.lang.ref.Cleaner.Cleanable;

import org.python.core.Py;
import org.python.core.PyCleaner;

class AllocatedNativeMemory extends BoundedNativeMemory implements AllocatedDirectMemory {
    private volatile boolean released = false;
    private volatile boolean autorelease = true;

    /** The real memory address */
    private final long storage;

    private final Cleanable cleanable;

    /**
     * Allocates native memory
     *
     * @param size The number of bytes to allocate
     * @param clear Whether the memory should be cleared (zeroed)
     * @return A new {@link AllocatedDirectMemory}
     */
    static final AllocatedNativeMemory allocate(int size, boolean clear) {
        return allocateAligned(size, 1, clear);
    }

    /**
     * Allocates native memory, aligned to a minimum boundary.
     *
     * @param size The number of bytes to allocate
     * @param align The minimum alignment of the memory
     * @param clear Whether the memory should be cleared (zeroed)
     * @return A new {@link AllocatedDirectMemory}
     */
    static final AllocatedNativeMemory allocateAligned(int size, int align, boolean clear) {
        long memory = IO.allocateMemory(size + align - 1, clear);
        if (memory == 0) {
            throw Py.RuntimeError("failed to allocate " + size + " bytes");
        }
        return new AllocatedNativeMemory(memory, size, align);
    }

    private AllocatedNativeMemory(long address, int size, int align) {
        super(((address - 1) & ~(align - 1)) + align, size);
        this.storage = address;
        System.out.println("+++ AllocatedNativeMemory: register doFinalize");
        this.cleanable = PyCleaner.INSTANCE.get().register(this, this::doFinalize);
    }

    public void free() {
        if (!released) {
            IO.freeMemory(storage);
            released = true;
        }
    }

    public void setAutoRelease(boolean release) {
        this.autorelease = release;
    }

    private void doFinalize() {
        System.out.println("+++ AllocatedNativeMemory::doFinalize");
        if (!released && autorelease) {
            IO.freeMemory(storage);
            released = true;
        }
    }

    @Override
    public void close() {
        cleanable.clean();
    }
}
