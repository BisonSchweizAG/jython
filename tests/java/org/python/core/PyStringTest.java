package org.python.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/** Odd tests of the Python {@code str} implementation that are best done from Java. */
class PyStringTest {

    private static final String RIGHT_SINGLE_QUOTATION_MARK = "\u2019"; // needs more than 8 bits
    private static final String JEANNE_DARC = "Jeanne d" + RIGHT_SINGLE_QUOTATION_MARK + "Arc";
    private static final String SMALL_O_UMLAUT = "\u00F6";
    private static final String BEAUTIFUL = "sch" + SMALL_O_UMLAUT + "n";

    /**
     * Construction of a {@code PyString} from a {@code String} with characters > {@code 0xff} results in an
     * {@code IllegalArgumentException} that quotes a safely-escaped version of the string.
     */
    @Test
    void preventNonBytePyString() {
        try {
            new PyString(JEANNE_DARC);
            fail("Expected an IllegalArgumentException which was not thrown");
        } catch (IllegalArgumentException iae) {
            String m = iae.getMessage();
            assertTrue(m.contains("Jeanne d\\u2019Arc"),
                            "Expected right single quotation mark not to fit into 8 bits of a PyString");
        }
    }

    @Test
    public void testPygetUmlaut() {
        PyString beautiful = Py.newString(BEAUTIFUL);
        assertFalse(beautiful instanceof PyUnicode);
        PyObject first = beautiful.pyget(0);
        assertTrue(first instanceof PyString);
        assertFalse(first instanceof PyUnicode);
        assertEquals("s", ((PyString) first).getString());
        PyObject umlaut = beautiful.pyget(3);
        assertTrue(umlaut instanceof PyString);
        assertFalse(umlaut instanceof PyUnicode);
        assertEquals(SMALL_O_UMLAUT, ((PyString) umlaut).getString());
    }

}
