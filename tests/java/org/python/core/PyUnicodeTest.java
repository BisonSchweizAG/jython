package org.python.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

public class PyUnicodeTest {


    private static final String BEAUTIFUL = "sch\u00F6n \n und \n t\u00F6ier";

    private PyUnicode pyUnicode;

    @Before
    public void setUp() {
        pyUnicode = new PyUnicode(BEAUTIFUL);
    }

    @Test
    public void testFromInterned() {
        String interned = BEAUTIFUL.intern();
        pyUnicode = PyUnicode.fromInterned(interned);
        assertSame(interned, pyUnicode.getString());
    }

    // quotes, no carriage returns
    @Test
    public void test__repr__() {
        PyString result = pyUnicode.__repr__();
        assertEquals("'" + "sch\u00F6n \\n und \\n t\u00F6ier" + "'", result.toString());
    }

    // no quotes, carriage returns preserved
    @Test
    public void test__str__() {
        PyString result = pyUnicode.__str__();
        assertEquals(BEAUTIFUL, result.toString());

    }

}
