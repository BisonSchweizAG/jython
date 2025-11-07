package org.python.compiler;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.python.core.BytecodeLoader;
import org.python.core.Py;
import org.python.core.PyObject;

/**
 * CustomMaker
 */
public class CustomMaker extends JavaMaker {

    /**
     * Constructor
     * 
     * @param superclass
     *            superclass
     * @param interfaces
     *            interfaces
     * @param pythonClass
     *            pythonClass
     * @param pythonModule
     *            pythonModule
     * @param myClass
     *            myClass
     * @param methods
     *            methods
     */
    public CustomMaker(Class<?> superclass,
                     Class<?>[] interfaces,
                     String pythonClass,
                     String pythonModule,
                     String myClass,
                     PyObject methods) {
        super(superclass, interfaces, pythonClass, pythonModule, myClass, methods);
    }

    /**
     * SaveBytes<br>
     * Override to save bytes
     * 
     * @param bytes
     *            bytes
     */
    public void saveBytes(ByteArrayOutputStream bytes) {
    }

    /**
     * MakeClass
     * <p>
     * By default makeClass will have the same behavior as MakeProxies calling JavaMaker, other than the debug behavior
     * of saving the classfile (as controlled by Options.ProxyDebugDirectory; users of CustomMaker simply need to save
     * it themselves).
     * <p>
     * Override this method to get custom classes built from any desired source.
     * 
     * @return class
     */
    public Class<?> makeClass() {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            build(bytes); // Side effect of writing to bytes
            saveBytes(bytes);
            List<Class<?>> secondary = new LinkedList<>(Arrays.asList(interfaces));
            List<Class<?>> referents = null;
            if (superclass != null) {
                secondary.add(0, superclass);
            }
            referents = secondary;
            return BytecodeLoader.makeClass(myClass, referents, bytes.toByteArray());
        } catch (Exception exc) {
            throw Py.JavaError(exc);
        }
    }
}
