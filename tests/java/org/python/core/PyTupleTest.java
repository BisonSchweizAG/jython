package org.python.core;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for PyTuple as Java Tuple.
 */
public class PyTupleTest {

    private PyTuple p = null;

    @Before
    public void setUp() throws Exception {
        PySystemState.initialize();
        p = new PyTuple(new PyString("foo"), new PyString("bar"));
    }

    @After
    public void tearDown() throws Exception {
        p = null;
    }

    // Test for http://bugs.jython.org/issue1419
    // "Bug in PyTuple.indexOf and PyTuple.indexOf"
    @Test
    public void testIndexOf() {
        PyTuple p = new PyTuple(new PyString("foo"), new PyString("bar"));
        assertEquals(0, p.indexOf("foo"));
        assertEquals(1, p.indexOf("bar"));
    }
    
    @Test
    public void testToArray() {
        // In Jython 2.5.0 if an array was passed into toArray() that was
        // too short, an Object[] was always returned instead of an array
        // of the proper type.
        Object[] test = new String[1];
        String[] s = (String[])p.toArray(test);
        assertEquals(s[0], "foo");
        assertEquals(s[1], "bar");
    }
}
