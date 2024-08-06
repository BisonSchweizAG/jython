package org.python.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class PyUnicodeTest {


    private static final String BEAUTIFUL = "sch\u00F6n \n und \n t\u00F6ier";
    private static final String LESS_BEAUTIFUL = "sch\u00F6an";
    private static final String MORE_BEAUTIFUL = "sch\u00F6zn";
    private static final String SMALL_O_UMLAUT = "\\u00F6";

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

    @Test
    public void test__eq__() {
        PyUnicode otherPyUnicode = new PyUnicode(BEAUTIFUL);
        assertPyTrue(pyUnicode.__eq__(otherPyUnicode));
        otherPyUnicode = new PyUnicode("gugus");
        assertPyFalse(pyUnicode.__eq__(otherPyUnicode));
    }

    @Test
    public void test__eq__PyString() {
        PyString otherPyString = new PyString(BEAUTIFUL);
        assertPyTrue(pyUnicode.__eq__(otherPyString));
        otherPyString = new PyString("gugus");
        assertPyFalse(pyUnicode.__eq__(otherPyString));
    }

    @Test
    public void test__ne__() {
        PyUnicode otherPyUnicode = new PyUnicode(BEAUTIFUL);
        assertPyFalse(pyUnicode.__ne__(otherPyUnicode));
        otherPyUnicode = new PyUnicode("gugus");
        assertPyTrue(pyUnicode.__ne__(otherPyUnicode));
    }

    @Test
    public void test__ne__PyString() {
        PyString otherPyString = new PyString(BEAUTIFUL);
        assertPyFalse(pyUnicode.__ne__(otherPyString));
        otherPyString = new PyString("gugus");
        assertPyTrue(pyUnicode.__ne__(otherPyString));
    }

    @Test
    public void test__lt__() {
        PyUnicode otherPyUnicode = new PyUnicode(LESS_BEAUTIFUL);
        assertPyTrue(otherPyUnicode.__lt__(pyUnicode));
        assertPyFalse(pyUnicode.__lt__(otherPyUnicode));
    }

    @Test
    public void test__lt__PyString() {
        PyString otherPyString = new PyString(LESS_BEAUTIFUL);
        assertPyTrue(otherPyString.__lt__(pyUnicode));
        assertPyFalse(pyUnicode.__lt__(otherPyString));
    }

    @Test
    public void test__le__() {
        PyUnicode otherPyUnicode = new PyUnicode(LESS_BEAUTIFUL);
        assertPyTrue(otherPyUnicode.__le__(pyUnicode));
        assertPyFalse(pyUnicode.__le__(otherPyUnicode));
        otherPyUnicode = new PyUnicode(BEAUTIFUL);
        assertPyTrue(otherPyUnicode.__le__(pyUnicode));
        assertPyTrue(pyUnicode.__le__(otherPyUnicode));
    }

    @Test
    public void test__le__PyString() {
        PyString otherPyString = new PyString(LESS_BEAUTIFUL);
        assertPyTrue(otherPyString.__le__(pyUnicode));
        assertPyFalse(pyUnicode.__le__(otherPyString));
        otherPyString = new PyString(BEAUTIFUL);
        assertPyTrue(otherPyString.__le__(pyUnicode));
        assertPyTrue(pyUnicode.__le__(otherPyString));
    }

    @Test
    public void test__gt__() {
        PyUnicode otherPyUnicode = new PyUnicode(MORE_BEAUTIFUL);
        assertPyTrue(otherPyUnicode.__gt__(pyUnicode));
        assertPyFalse(pyUnicode.__gt__(otherPyUnicode));
    }

    @Test
    public void test__gt__PyString() {
        PyString otherPyString = new PyString(MORE_BEAUTIFUL);
        assertPyTrue(otherPyString.__gt__(pyUnicode));
        assertPyFalse(pyUnicode.__gt__(otherPyString));
    }

    @Test
    public void test__ge__() {
        PyUnicode otherPyUnicode = new PyUnicode(MORE_BEAUTIFUL);
        assertPyTrue(otherPyUnicode.__ge__(pyUnicode));
        assertPyFalse(pyUnicode.__ge__(otherPyUnicode));
        otherPyUnicode = new PyUnicode(BEAUTIFUL);
        assertPyTrue(otherPyUnicode.__ge__(pyUnicode));
        assertPyTrue(pyUnicode.__ge__(otherPyUnicode));
    }

    @Test
    public void test__ge__PyString() {
        PyString otherPyString = new PyString(MORE_BEAUTIFUL);
        assertPyTrue(otherPyString.__ge__(pyUnicode));
        assertPyFalse(pyUnicode.__ge__(otherPyString));
        otherPyString = new PyString(BEAUTIFUL);
        assertPyTrue(otherPyString.__ge__(pyUnicode));
        assertPyTrue(pyUnicode.__ge__(otherPyString));
    }

    @Test
    public void test__len__() {
        assertEquals(BEAUTIFUL.length(), pyUnicode.__len__());
    }

    @Test
    public void testSubstring() {
        int start = 2;
        int end = 5;
        assertEquals(BEAUTIFUL.substring(start, end), pyUnicode.substring(start, end));
    }

    @Test
    public void test__unicode__() {
        assertSame(pyUnicode, pyUnicode.__unicode__());
    }

    @Test
    public void testUnicode_replaceBasicPyString() {
        PyString toBeReplaced = new PyString(SMALL_O_UMLAUT);
        PyString replacement = new PyString("oe");
        PyString result = pyUnicode.unicode_replace(toBeReplaced, replacement, -1); // all occurrences
        assertTrue(result instanceof PyUnicode);
        assertEquals("schoen \n und \n toeier", result.getString());
    }

    @Test
    public void testUnicode_replaceBasicPyUnicode() {
        PyString toBeReplaced = new PyUnicode(SMALL_O_UMLAUT);
        PyString replacement = new PyUnicode("oe");
        PyString result = pyUnicode.unicode_replace(toBeReplaced, replacement, -1); // all occurrences
        assertTrue(result instanceof PyUnicode);
        assertEquals("schoen \n und \n toeier", result.getString());
    }

    private void assertPyTrue(PyObject value) {
        assertTrue(value instanceof PyBoolean);
        assertTrue(((PyBoolean) value).__nonzero__());
    }

    private void assertPyFalse(PyObject value) {
        assertTrue(value instanceof PyBoolean);
        assertFalse(((PyBoolean) value).__nonzero__());
    }

}
