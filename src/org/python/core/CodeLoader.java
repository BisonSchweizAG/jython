package org.python.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * CodeLoader
 */
public final class CodeLoader {

    /** GET_BOOTSTRAP_METHOD_NAME */
    public static final String GET_BOOTSTRAP_METHOD_NAME = "getCodeBootstrap";
    /** name */
    public final String name;
    /** filename */
    public final String filename;

    private CodeLoader(String name, String filename) {
        this.name = name;
        this.filename = filename;
    }

    /**
     * CanLoad
     * 
     * @param cls
     *            cls
     * @return canLoad
     */
    public static boolean canLoad(Class<?> cls) {
        try {
            Method getBootstrap = cls.getMethod(GET_BOOTSTRAP_METHOD_NAME);
            return Modifier.isStatic(getBootstrap.getModifiers());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * LoadCode
     * 
     * @param cls
     *            cls
     * @param name
     *            name
     * @param filename
     *            filename
     * @return code
     * @throws SecurityException
     *             securityException
     * @throws IllegalArgumentException
     *             illegalArgumentException
     * @throws NoSuchMethodException
     *             noSuchMethodException
     * @throws IllegalAccessException
     *             illegalAccessException
     * @throws InvocationTargetException
     *             invoationTargetException
     */
    public static PyCode loadCode(Class<?> cls, String name, String filename)
            throws SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        Method getBootstrap = cls.getMethod(GET_BOOTSTRAP_METHOD_NAME);
        CodeBootstrap bootstrap = (CodeBootstrap) getBootstrap.invoke(null);
        return loadCode(bootstrap, name, filename);
    }

    /**
     * LoadCode
     * 
     * @param cls
     *            cls
     * @return code
     * @throws SecurityException
     *             securityException
     * @throws IllegalArgumentException
     *             illegalArgumentException
     * @throws NoSuchMethodException
     *             noSuchMethodException
     * @throws IllegalAccessException
     *             illegalAccessException
     * @throws InvocationTargetException
     *             invoationTargetException
     */
    public static PyCode loadCode(Class<?> cls) throws SecurityException,
            IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        return loadCode(cls, null, null);
    }

    /**
     * LoadCode
     * 
     * @param bootstrap
     *            bootstrap
     * @param name
     *            name
     * @param filename
     *            filename
     * @return code
     */
    public static PyCode loadCode(CodeBootstrap bootstrap, String name,
            String filename) {
        return bootstrap.loadCode(new CodeLoader(name, filename));
    }

    /**
     * LoadCode
     * 
     * @param bootstrap
     *            bootstrap
     * @return code
     */
    public static PyCode loadCode(CodeBootstrap bootstrap) {
        return loadCode(bootstrap, null, null);
    }

    /** SIMPLE_FACTORY_METHOD_NAME */
    public static final String SIMPLE_FACTORY_METHOD_NAME = "createSimpleBootstrap";

    /**
     * CreateSimpleBootstrap
     * 
     * @param code
     *            code
     * @return code
     */
    public static CodeBootstrap createSimpleBootstrap(final PyCode code) {
        return new CodeBootstrap() {
            public PyCode loadCode(CodeLoader loader) {
                return code;
            }
        };
    }
}
