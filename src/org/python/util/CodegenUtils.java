/*
 * Initial code taken from Charlie Nutter's org.jruby.util.CodegenUtils.
 */

package org.python.util;

import java.util.Arrays;

/**
 * CodegenUtils
 */
public class CodegenUtils {

    /** Default constructor */
    CodegenUtils() {
    }

    /**
     * Creates a dotted class name from a path/package name
     * 
     * @param p
     *            p
     * @return s
     */
    public static String c(String p) {
        return p.replace('/', '.');
    }

    /**
     * Creates a class path name, from a Class.
     * 
     * @param n
     *            n
     * @return s
     */
    public static String p(Class n) {
        return n.getName().replace('.','/');
    }

    /**
     * Creates a class identifier of form Labc/abc;, from a Class.
     * 
     * @param n
     *            n
     * @return s
     */
    public static String ci(Class n) {
        if (n.isArray()) {
            n = n.getComponentType();
            if (n.isPrimitive()) {
                if (n == Byte.TYPE) {
                    return "[B";
                } else if (n == Boolean.TYPE) {
                    return "[Z";
                } else if (n == Short.TYPE) {
                    return "[S";
                } else if (n == Character.TYPE) {
                    return "[C";
                } else if (n == Integer.TYPE) {
                    return "[I";
                } else if (n == Float.TYPE) {
                    return "[F";
                } else if (n == Double.TYPE) {
                    return "[D";
                } else if (n == Long.TYPE) {
                    return "[J";
                } else {
                    throw new RuntimeException("Unrecognized type in compiler: " + n.getName());
                }
            } else {
                return "[" + ci(n);
            }
        } else {
            if (n.isPrimitive()) {
                if (n == Byte.TYPE) {
                    return "B";
                } else if (n == Boolean.TYPE) {
                    return "Z";
                } else if (n == Short.TYPE) {
                    return "S";
                } else if (n == Character.TYPE) {
                    return "C";
                } else if (n == Integer.TYPE) {
                    return "I";
                } else if (n == Float.TYPE) {
                    return "F";
                } else if (n == Double.TYPE) {
                    return "D";
                } else if (n == Long.TYPE) {
                    return "J";
                } else if (n == Void.TYPE) {
                    return "V";
                } else {
                    throw new RuntimeException("Unrecognized type in compiler: " + n.getName());
                }
            } else {
                return "L" + p(n) + ";";
            }
        }
    }

    /**
     * Create a method signature from the given param types and return values
     * 
     * @param retval
     *            retval
     * @param params
     *            params
     * @return s
     */
    public static String sig(Class retval, Class... params) {
        return sigParams(params) + ci(retval);
    }

    /**
     * Sig
     * 
     * @param retval
     *            retval
     * @param descriptor
     *            descriptor
     * @param params
     *            params
     * @return s
     */
    public static String sig(Class retval, String descriptor, Class... params) {
        return sigParams(descriptor, params) + ci(retval);
    }

    /**
     * SigParams
     * 
     * @param params
     *            params
     * @return s
     */
    public static String sigParams(Class... params) {
        StringBuilder signature = new StringBuilder("(");
        
        for (int i = 0; i < params.length; i++) {
            signature.append(ci(params[i]));
        }
        
        signature.append(")");
        
        return signature.toString();
    }

    /**
     * SigParams
     * 
     * @param descriptor
     *            descriptor
     * @param params
     *            params
     * @return s
     */
    public static String sigParams(String descriptor, Class... params) {
        StringBuilder signature = new StringBuilder("(");

        signature.append(descriptor);
        
        for (int i = 0; i < params.length; i++) {
            signature.append(ci(params[i]));
        }

        signature.append(")");

        return signature.toString();
    }
    
    /**
     * Params
     * 
     * @param classes
     *            classes
     * @return classes
     */
    public static Class[] params(Class... classes) {
        return classes;
    }
    
    /**
     * Params
     * 
     * @param cls
     *            cls
     * @param times
     *            times
     * @return classes
     */
    public static Class[] params(Class cls, int times) {
        Class[] classes = new Class[times];
        Arrays.fill(classes, cls);
        return classes;
    }

    /**
     * Params
     * 
     * @param cls1
     *            cls1
     * @param clsFill
     *            clsFill
     * @param times
     *            times
     * @return classes
     */
    public static Class[] params(Class cls1, Class clsFill, int times) {
        Class[] classes = new Class[times + 1];
        Arrays.fill(classes, clsFill);
        classes[0] = cls1;
        return classes;
    }
    
}
