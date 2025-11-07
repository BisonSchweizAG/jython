/*
 * CodegenUtils.java
 *
 * Created on January 31, 2007, 11:54 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.python.modules.jffi;

import java.util.Arrays;
import java.util.Map;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Type;

/**
 * Code generation utilities
 * 
 * @author headius
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
     * Creates a human-readable representation, from a Class.
     * 
     * @param n
     *            n
     * @return s
     */
    public static String human(Class n) {
        return n.getCanonicalName();
    }

    /**
     * HumanShort
     * 
     * @param n
     *            n
     * @return s
     */
    public static String humanShort(Class n) {
        return n.getSimpleName();
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
     * @param retvalParams
     *            retvalParams
     * @return s
     */
    public static String sig(Class[] retvalParams) {
        Class[] justParams = new Class[retvalParams.length - 1];
        System.arraycopy(retvalParams, 1, justParams, 0, justParams.length);
        return sigParams(justParams) + ci(retvalParams[0]);
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
     * Pretty
     * 
     * @param retval
     *            retval
     * @param params
     *            params
     * @return s
     */
    public static String pretty(Class retval, Class... params) {
        return prettyParams(params) + human(retval);
    }

    /**
     * PrettyParams
     * 
     * @param params
     *            params
     * @return s
     */
    public static String prettyParams(Class... params) {
        StringBuilder signature = new StringBuilder("(");
        
        for (int i = 0; i < params.length; i++) {
            signature.append(human(params[i]));
            if (i < params.length - 1) signature.append(',');
        }
        
        signature.append(")");
        
        return signature.toString();
    }
    
    /**
     * PrettyShortParams
     * 
     * @param params
     *            params
     * @return s
     */
    public static String prettyShortParams(Class... params) {
        StringBuilder signature = new StringBuilder("(");
        
        for (int i = 0; i < params.length; i++) {
            signature.append(humanShort(params[i]));
            if (i < params.length - 1) signature.append(',');
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

    /**
     * Params
     * 
     * @param cls1
     *            cls1
     * @param cls2
     *            cls2
     * @param clsFill
     *            clsFill
     * @param times
     *            times
     * @return classes
     */
    public static Class[] params(Class cls1, Class cls2, Class clsFill, int times) {
        Class[] classes = new Class[times + 2];
        Arrays.fill(classes, clsFill);
        classes[0] = cls1;
        classes[1] = cls2;
        return classes;
    }
    
    /**
     * GetAnnotatedBindingClassName
     * 
     * @param javaMethodName
     *            javaMethodName
     * @param typeName
     *            typeName
     * @param isStatic
     *            isStatic
     * @param required
     *            required
     * @param optional
     *            optional
     * @param multi
     *            multi
     * @param framed
     *            framed
     * @return s
     */
    public static String getAnnotatedBindingClassName(String javaMethodName, String typeName, boolean isStatic, int required, int optional, boolean multi, boolean framed) {
        String commonClassSuffix;
        if (multi) {
            commonClassSuffix = (isStatic ? "$s$" : "$i$" ) + javaMethodName;
        } else {
            commonClassSuffix = (isStatic ? "$s$" : "$i$" ) + required + "$" + optional + "$" + javaMethodName;
        }
        return typeName + commonClassSuffix;
    }

    /**
     * VisitAnnotationFields
     * 
     * @param visitor
     *            visitor
     * @param fields
     *            fields
     */
    public static void visitAnnotationFields(AnnotationVisitor visitor, Map<String, Object> fields) {
        for (Map.Entry<String, Object> fieldEntry : fields.entrySet()) {
            Object value = fieldEntry.getValue();
            if (value.getClass().isArray()) {
                Object[] values = (Object[]) value;
                AnnotationVisitor arrayV = visitor.visitArray(fieldEntry.getKey());
                for (int i = 0; i < values.length; i++) {
                    arrayV.visit(null, values[i]);
                }
                arrayV.visitEnd();
            } else if (value.getClass().isEnum()) {
                visitor.visitEnum(fieldEntry.getKey(), ci(value.getClass()), value.toString());
            } else if (value instanceof Class) {
                visitor.visit(fieldEntry.getKey(), Type.getType((Class) value));
            } else {
                visitor.visit(fieldEntry.getKey(), value);
            }
        }
    }

    /**
     * GetBoxType
     * 
     * @param type
     *            type
     * @return class
     */
    public static Class getBoxType(Class type) {
        if (type == int.class) {
            return Integer.class;
        } else if (type == byte.class) {
            return Byte.class;
        } else if (type == short.class) {
            return Short.class;
        } else if (type == char.class) {
            return Character.class;
        } else if (type == long.class) {
            return Long.class;
        } else if (type == float.class) {
            return Float.class;
        } else if (type == double.class) {
            return Double.class;
        } else if (type == boolean.class) {
            return Boolean.class;
        } else {
            throw new RuntimeException("Not a native type: " + type);
        }
    }
}
