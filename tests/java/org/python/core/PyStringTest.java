package org.python.core;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

/** Odd tests of the Python {@code str} implementation that are best done from Java. */
public class PyStringTest {

    public static final String RIGHT_SINGLE_QUOTATION_MARK = "\u2019"; // needs more than 8 bits
    public static final String JEANNE_DARC = "Jeanne d" + RIGHT_SINGLE_QUOTATION_MARK + "Arc";

    /**
     * Construction of a {@code PyString} from a {@code String} with characters > {@code 0xff} results in an
     * {@code IllegalArgumentException} that quotes a safely-escaped version of the string.
     */
    @Test
    public void preventNonBytePyString() {
        try {
            new PyString(JEANNE_DARC);
            fail("Expected an IllegalArgumentException which was not thrown");
        } catch (IllegalArgumentException iae) {
            String m = iae.getMessage();
            assertTrue("Expected right single quotation mark not to fit into 8 bits of a PyString",
                            m.contains("Jeanne d\\u2019Arc"));
        }
    }
}
