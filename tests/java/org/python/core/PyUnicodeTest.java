package org.python.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PyUnicodeTest {

    private static final String RIGHT_SINGLE_QUOTATION_MARK = "\u2019"; // needs more than 8 bits
    private static final String JEANNE_DARC = "Jeanne d" + RIGHT_SINGLE_QUOTATION_MARK + "Arc";

    @Test
    void testPygetUnicode() {
        PyString beautiful = Py.newString(JEANNE_DARC);
        assertTrue(beautiful instanceof PyUnicode);
        PyObject first = beautiful.pyget(0);
        assertTrue(first instanceof PyUnicode);
        assertEquals("J", ((PyUnicode) first).getString());
        PyObject unicode = beautiful.pyget(8);
        assertTrue(unicode instanceof PyUnicode);
        assertEquals(RIGHT_SINGLE_QUOTATION_MARK, ((PyUnicode) unicode).getString());
    }

}
