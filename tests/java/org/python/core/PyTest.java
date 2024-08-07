package org.python.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class PyTest {

    private static final String FRENCH_APOSTROPHE = "\u02B9";
    private static final String RIGHT_SINGLE_QUOTATION_MARK = "\u2019";
    private static final String TURKISH_C = "\u00E7";
    private static final String TURKISH_I = "\u0131";
    private static final String TURKISH_S = "\u015F";

    @Test
    public void testNewStringFrench() {
        String input = "Rabais d" + FRENCH_APOSTROPHE + "action annuel %";
        PyString pyString = Py.newString(input);
        assertEquals(input, pyString.getString());
    }

    @Test
    public void testNewStringFrenchSimple() {
        String input = "Rabais d" + RIGHT_SINGLE_QUOTATION_MARK + "action annuel %";
        System.out.println(input);
        PyString pyString = Py.newString(input);
        assertEquals(input, pyString.getString());
    }

    @Test
    public void testNewStringTurkish() {
        String input = "Sa" + TURKISH_C + "l" + TURKISH_I + " me" + TURKISH_S + "e";
        PyString pyString = Py.newString(input);
        assertEquals(input, pyString.getString());
    }

    @Test
    public void testNewStringNull() {
        try {
        Py.newString(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Cannot create PyString from null", e.getMessage());
        }
    }

}
