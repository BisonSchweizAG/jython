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
    private static final String SMALL_O_UMLAUT = "\u00F6";

    private static final String FRENCH_APOSTROPHE = "\u02B9";
    private static final String TURKISH_C = "\u00E7";
    private static final String TURKISH_I = "\u0131";

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
    public void test__str__French() {
        String frenchString = "Rabais d" + FRENCH_APOSTROPHE + "action annuel %";
        PyString frenchPyString = new PyString(frenchString);

        PyString result = frenchPyString.__str__();
        assertEquals(frenchString, result.toString());
    }

    @Test
    public void test__str__Turkish() {
        String turkishString = "Sa" + TURKISH_C + "l" + TURKISH_I;
        PyString turkishPyString = new PyString(turkishString);

        PyString result = turkishPyString.__str__();
        assertEquals(turkishString, result.toString());
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

    @Test
    public void test__add__() {
        PyUnicode addition = new PyUnicode(" und h\u00E4rzig");
        PyObject result = pyUnicode.__add__(addition);
        assertTrue(result instanceof PyString);
        assertEquals(BEAUTIFUL.concat(addition.getString()), ((PyString) result).getString());
    }

    @Test
    public void test__add__PyString() {
        PyString addition = new PyString(" und h\\u00E4rzig");
        PyObject result = pyUnicode.__add__(addition);
        assertTrue(result instanceof PyString);
        assertEquals(BEAUTIFUL.concat(addition.getString()), ((PyString) result).getString());
    }

    @Test
    public void testUnicode_stripReallyBasic() {
        PyUnicode toBeStripped = new PyUnicode("abc");
        PyUnicode originalPyUnicode = new PyUnicode("abcabcGUGUSabc");
        PyObject result = originalPyUnicode.unicode_strip(toBeStripped);
        assertTrue(result instanceof PyString);
        assertEquals("GUGUS", ((PyString) result).getString());
    }

    @Test
    public void testUnicode_strip() {
        PyUnicode toBeStripped = new PyUnicode(SMALL_O_UMLAUT + SMALL_O_UMLAUT);
        String originalString = SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT //
                        + "GUGUS" //
                        + SMALL_O_UMLAUT + SMALL_O_UMLAUT;
        PyUnicode originalPyUnicode = new PyUnicode(originalString);
        PyObject result = originalPyUnicode.unicode_strip(toBeStripped);
        assertTrue(result instanceof PyString);
        assertEquals("GUGUS", ((PyString) result).getString());
    }

    @Test
    public void testUnicode_stripPyString() {
        PyString toBeStripped = new PyString(SMALL_O_UMLAUT + SMALL_O_UMLAUT);
        String originalString = SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT //
                        + "GUGUS" //
                        + SMALL_O_UMLAUT + SMALL_O_UMLAUT;
        PyUnicode originalPyUnicode = new PyUnicode(originalString);
        PyObject result = originalPyUnicode.unicode_strip(toBeStripped);
        assertTrue(result instanceof PyString);
        assertEquals("GUGUS", ((PyString) result).getString());
    }

    @Test
    public void testStrip() {
        PyUnicode toBeStripped = new PyUnicode(SMALL_O_UMLAUT + SMALL_O_UMLAUT);
        String originalString = SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT //
                        + "GUGUS" //
                        + SMALL_O_UMLAUT + SMALL_O_UMLAUT;
        PyUnicode originalPyUnicode = new PyUnicode(originalString);
        PyObject result = originalPyUnicode.strip(toBeStripped);
        assertTrue(result instanceof PyString);
        assertEquals("GUGUS", ((PyString) result).getString());
    }

    @Test
    public void testStripPyString() {
        PyString toBeStripped = new PyString(SMALL_O_UMLAUT + SMALL_O_UMLAUT);
        String originalString = SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT //
                        + "GUGUS" //
                        + SMALL_O_UMLAUT + SMALL_O_UMLAUT;
        PyUnicode originalPyUnicode = new PyUnicode(originalString);
        PyObject result = originalPyUnicode.strip(toBeStripped);
        assertTrue(result instanceof PyString);
        assertEquals("GUGUS", ((PyString) result).getString());
    }

    @Test
    public void testLStrip() {
        PyUnicode toBeStripped = new PyUnicode(SMALL_O_UMLAUT + SMALL_O_UMLAUT);
        String originalString = SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT //
                        + "GUGUS" //
                        + SMALL_O_UMLAUT + SMALL_O_UMLAUT;
        PyUnicode originalPyUnicode = new PyUnicode(originalString);
        PyObject result = originalPyUnicode.lstrip(toBeStripped);
        assertTrue(result instanceof PyString);
        assertEquals("GUGUS" + SMALL_O_UMLAUT + SMALL_O_UMLAUT, ((PyString) result).getString());
    }

    @Test
    public void testLStripPyString() {
        PyString toBeStripped = new PyString(SMALL_O_UMLAUT + SMALL_O_UMLAUT);
        String originalString = SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT //
                        + "GUGUS" //
                        + SMALL_O_UMLAUT + SMALL_O_UMLAUT;
        PyUnicode originalPyUnicode = new PyUnicode(originalString);
        PyObject result = originalPyUnicode.lstrip(toBeStripped);
        assertTrue(result instanceof PyString);
        assertEquals("GUGUS" + SMALL_O_UMLAUT + SMALL_O_UMLAUT, ((PyString) result).getString());
    }

    @Test
    public void testRStrip() {
        PyUnicode toBeStripped = new PyUnicode(SMALL_O_UMLAUT + SMALL_O_UMLAUT);
        String originalString = SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT //
                        + "GUGUS" //
                        + SMALL_O_UMLAUT + SMALL_O_UMLAUT;
        PyUnicode originalPyUnicode = new PyUnicode(originalString);
        PyObject result = originalPyUnicode.rstrip(toBeStripped);
        assertTrue(result instanceof PyString);
        assertEquals(SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT + "GUGUS",
                        ((PyString) result).getString());
    }

    @Test
    public void testRStripPyString() {
        PyString toBeStripped = new PyString(SMALL_O_UMLAUT + SMALL_O_UMLAUT);
        String originalString = SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT //
                        + "GUGUS" //
                        + SMALL_O_UMLAUT + SMALL_O_UMLAUT;
        PyUnicode originalPyUnicode = new PyUnicode(originalString);
        PyObject result = originalPyUnicode.rstrip(toBeStripped);
        assertTrue(result instanceof PyString);
        assertEquals(SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT + "GUGUS",
                        ((PyString) result).getString());
    }

    @Test
    public void test__contains__() {
        PyUnicode searchString = new PyUnicode(SMALL_O_UMLAUT);
        assertTrue(pyUnicode.__contains__(searchString));
    }

    @Test
    public void test__contains__PyString() {
        PyString searchString = new PyString(SMALL_O_UMLAUT);
        assertTrue(pyUnicode.__contains__(searchString));
    }

    @Test
    public void testSplit() {
        PySystemState.initialize();
        PyList pyList = pyUnicode.split(" ");
        assertEquals(5, pyList.__len__());
        assertEquals("sch\u00F6n", pyList.get(0));
        assertEquals("\n", pyList.get(1));
        assertEquals("und", pyList.get(2));
        assertEquals("\n", pyList.get(3));
        assertEquals("t\u00F6ier", pyList.get(4));
    }

    @Test
    public void testSplitSepP() {
        PySystemState.initialize();
        PyUnicode sep = new PyUnicode(SMALL_O_UMLAUT);
        PyList pyList = pyUnicode.split(sep);
        assertEquals(3, pyList.__len__());
        assertEquals("sch", pyList.get(0));
        assertEquals("n \n und \n t", pyList.get(1));
        assertEquals("ier", pyList.get(2));
    }

    @Test
    public void testSplitSepPyString() {
        PySystemState.initialize();
        PyString sep = new PyString(SMALL_O_UMLAUT);
        PyList pyList = pyUnicode.split(sep);
        assertEquals(3, pyList.__len__());
        assertEquals("sch", pyList.get(0));
        assertEquals("n \n und \n t", pyList.get(1));
        assertEquals("ier", pyList.get(2));
    }

    @Test
    public void testRsplit() {
        PySystemState.initialize();
        PyList pyList = pyUnicode.rsplit(" ");
        assertEquals(5, pyList.__len__());
        assertEquals("sch\u00F6n", pyList.get(0));
        assertEquals("\n", pyList.get(1));
        assertEquals("und", pyList.get(2));
        assertEquals("\n", pyList.get(3));
        assertEquals("t\u00F6ier", pyList.get(4));
    }

    @Test
    public void testRsplitSepP() {
        PySystemState.initialize();
        PyUnicode sep = new PyUnicode(SMALL_O_UMLAUT);
        PyList pyList = pyUnicode.rsplit(sep);
        assertEquals(3, pyList.__len__());
        assertEquals("sch", pyList.get(0));
        assertEquals("n \n und \n t", pyList.get(1));
        assertEquals("ier", pyList.get(2));
    }

    @Test
    public void testRsplitSepPyString() {
        PySystemState.initialize();
        PyString sep = new PyString(SMALL_O_UMLAUT);
        PyList pyList = pyUnicode.rsplit(sep);
        assertEquals(3, pyList.__len__());
        assertEquals("sch", pyList.get(0));
        assertEquals("n \n und \n t", pyList.get(1));
        assertEquals("ier", pyList.get(2));
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
