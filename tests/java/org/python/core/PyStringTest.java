package org.python.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

public class PyStringTest {

    private static final String BEAUTIFUL = "sch\u00F6n \n und \n t\u00F6ier";

    private PyString pyString;

    @Before
    public void setUp() {
        pyString = new PyString(BEAUTIFUL);
    }

    @Test
    public void testToString() {
        assertEquals(BEAUTIFUL, pyString.toString());
    }

    @Test
    public void testGetString() {
        assertEquals(BEAUTIFUL, pyString.getString());
    }

    @Test
    public void testCharAt() {
        assertEquals(BEAUTIFUL.charAt(3), pyString.charAt(3));
    }

    @Test
    public void testLength() {
        assertEquals(BEAUTIFUL.length(), pyString.length());
    }

    @Test
    public void testSubSequence() {
        assertEquals(BEAUTIFUL.subSequence(2, 4), pyString.subSequence(2, 4));
    }

    @Test
    public void testInternedString() {
        assertSame(BEAUTIFUL.intern(), pyString.internedString());
    }

    @Test
    public void testFromInterned() {
        String interned = BEAUTIFUL.intern();
        pyString = PyString.fromInterned(interned);
        assertSame(interned, pyString.getString());
    }

    // quotes, no carriage returns
    @Test
    public void test__repr__() {
        PyString result = pyString.__repr__();
        assertEquals("'" + "sch\u00F6n \\n und \\n t\u00F6ier" + "'", result.toString());

    }

    // no quotes, carriage returns preserved
    @Test
    public void test__str__() {
        PyString result = pyString.__str__();
        assertEquals(BEAUTIFUL, result.toString());
    }

    @Test
    public void testCoercePyString() {
        assertEquals(BEAUTIFUL, PyString.coerce(new PyString(BEAUTIFUL)));
    }

    @Test
    public void testCoercePyUnicode() {
        assertEquals(BEAUTIFUL, PyString.coerce(new PyUnicode(BEAUTIFUL)));
    }

    @Test
    public void testCoerceAny() {
        assertNull(PyString.coerce(new PyObject()));
    }

}
