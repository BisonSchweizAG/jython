package org.python.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PyTest {

    public static final String SMALL_O_UMLAUT = "\u00F6"; // fits into 8 bits
    public static final String RIGHT_SINGLE_QUOTATION_MARK = "\u2019"; // needs more than 8 bits
    public static final String BEAUTIFUL = "sch" + SMALL_O_UMLAUT + "n";
    public static final String JEANNE_DARC = "Jeanne d" + RIGHT_SINGLE_QUOTATION_MARK + "Arc";

    @Test
    void testNewString() {
        PyString pyString = Py.newString("abc");
        assertFalse(pyString instanceof PyUnicode);
        assertEquals("abc", pyString.toString());
    }

    @Test
    void testNewStringUmlaut() {
        PyString pyString = Py.newString(BEAUTIFUL);
        assertFalse(pyString instanceof PyUnicode, "Umlaut should still fit into 8 bits of a PyString");
        assertEquals(BEAUTIFUL, pyString.toString());
    }

    @Test
    void testNewStringUnicode() {
        PyString pyString = Py.newString(JEANNE_DARC);
        assertTrue(pyString instanceof PyUnicode);
        assertEquals(JEANNE_DARC, pyString.toString());
    }

    @Test
    void testNewStringOrUnicodePrecedentUnicode() {
        PyUnicode precedent = Py.newUnicode(RIGHT_SINGLE_QUOTATION_MARK);
        PyString unicode = Py.newStringOrUnicode(precedent, "simple");
        assertTrue(unicode instanceof PyUnicode, "ASCII should still fit into a PyString");
        assertEquals("simple", unicode.getString());
    }

    @Test
    void testNewStringOrUnicodeUmlaut() {
        PyString umlaut = Py.newStringOrUnicode(BEAUTIFUL);
        assertFalse(umlaut instanceof PyUnicode, "Umlaut should still fit into 8 bits of a PyString");
        assertEquals(BEAUTIFUL, umlaut.getString());
    }

    @Test
    void testNewStringOrUnicodeRealUnicode() {
        PyString unicode = Py.newStringOrUnicode(JEANNE_DARC);
        assertTrue(unicode instanceof PyUnicode);
        assertEquals(JEANNE_DARC, unicode.getString());
    }

    @Test
    // used to convert the result of a pure Java method call into a PyObject
    void testJava2Py() {
        PyObject pyObject = Py.java2py("abc");
        assertTrue(pyObject instanceof PyString);
        assertFalse(pyObject instanceof PyUnicode, "ASCII should still fit into a PyString");
        assertEquals("abc", ((PyString) pyObject).toString());
    }

    // java2py: used to convert the result of a pure Java method call into a PyObject

    @Test
    void testJava2PyUmlaut() {
        PyObject pyObject = Py.java2py(BEAUTIFUL);
        assertTrue(pyObject instanceof PyString);
        assertFalse(pyObject instanceof PyUnicode, "Umlaut should still fit into 8 bits of a PyString");
        assertEquals(BEAUTIFUL, ((PyString) pyObject).toString());
    }

    @Test
    void testJava2PyUnicode() {
        PyObject pyObject = Py.java2py(JEANNE_DARC);
        assertTrue(pyObject instanceof PyUnicode);
        assertEquals(JEANNE_DARC, ((PyUnicode) pyObject).toString());
    }

    @Test
    void testJava2PySingleChar() {
        PyObject pyObject = Py.java2py(Character.valueOf('a'));
        assertTrue(pyObject instanceof PyString);
        assertFalse(pyObject instanceof PyUnicode, "ASCII should still fit into a PyString");
        assertEquals("a", ((PyString) pyObject).toString());
    }

    @Test
    void testJava2PySingleCharUmlaut() {
        PyObject pyObject = Py.java2py(Character.valueOf(SMALL_O_UMLAUT.charAt(0)));
        assertTrue(pyObject instanceof PyString);
        assertFalse(pyObject instanceof PyUnicode, "Umlaut should still fit into 8 bits of a PyString");
        assertEquals(SMALL_O_UMLAUT, ((PyString) pyObject).toString());
    }

    @Test
    void testJava2PySingleCharUnicode() {
        PyObject pyObject = Py.java2py(Character.valueOf(RIGHT_SINGLE_QUOTATION_MARK.charAt(0)));
        assertTrue(pyObject instanceof PyUnicode);
        assertEquals(RIGHT_SINGLE_QUOTATION_MARK, ((PyUnicode) pyObject).toString());
    }

}
