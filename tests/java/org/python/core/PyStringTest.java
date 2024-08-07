package org.python.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class PyStringTest {

    private static final String BEAUTIFUL = "sch\u00F6n \n und \n t\u00F6ier";
    private static final String LESS_BEAUTIFUL = "sch\u00F6an";
    private static final String MORE_BEAUTIFUL = "sch\u00F6zn";
    private static final String SMALL_O_UMLAUT = "\u00F6";

    private static final String FRENCH_APOSTROPHE = "\u02B9";
    private static final String TURKISH_C = "\u00E7";
    private static final String TURKISH_I = "\u0131";

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
    public void test__len__() {
        assertEquals(BEAUTIFUL.length(), pyString.__len__());
    }

    @Test
    public void testSubSequence() {
        assertEquals(BEAUTIFUL.subSequence(2, 4), pyString.subSequence(2, 4));
    }

    @Test
    public void testSubstring() {
        int start = 2;
        int end = 5;
        assertEquals(BEAUTIFUL.substring(start, end), pyString.substring(start, end));
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

    @Test
    public void test__eq__() {
        PyString otherPyString = new PyString(BEAUTIFUL);
        assertPyTrue(pyString.__eq__(otherPyString));
        otherPyString = new PyString("gugus");
        assertPyFalse(pyString.__eq__(otherPyString));
    }

    @Test
    public void test__eq__PyUnicode() {
        PyUnicode otherPyUnicode = new PyUnicode(BEAUTIFUL);
        assertPyTrue(pyString.__eq__(otherPyUnicode));
        otherPyUnicode = new PyUnicode("gugus");
        assertPyFalse(pyString.__eq__(otherPyUnicode));
    }

    @Test
    public void test__ne__() {
        PyString otherPyString = new PyString(BEAUTIFUL);
        assertPyFalse(pyString.__ne__(otherPyString));
        otherPyString = new PyString("gugus");
        assertPyTrue(pyString.__ne__(otherPyString));
    }

    @Test
    public void test__ne__PyUnicode() {
        PyUnicode otherPyUnicode = new PyUnicode(BEAUTIFUL);
        assertPyFalse(pyString.__ne__(otherPyUnicode));
        otherPyUnicode = new PyUnicode("gugus");
        assertPyTrue(pyString.__ne__(otherPyUnicode));
    }

    @Test
    public void test__lt__() {
        PyString otherPyString = new PyString(LESS_BEAUTIFUL);
        assertPyTrue(otherPyString.__lt__(pyString));
        assertPyFalse(pyString.__lt__(otherPyString));
    }

    @Test
    public void test__lt__PyUnicode() {
        PyUnicode otherPyUnicode = new PyUnicode(LESS_BEAUTIFUL);
        assertPyTrue(otherPyUnicode.__lt__(pyString));
        assertPyFalse(pyString.__lt__(otherPyUnicode));
    }

    @Test
    public void test__le__() {
        PyString otherPyString = new PyString(LESS_BEAUTIFUL);
        assertPyTrue(otherPyString.__le__(pyString));
        assertPyFalse(pyString.__le__(otherPyString));
        otherPyString = new PyString(BEAUTIFUL);
        assertPyTrue(otherPyString.__le__(pyString));
        assertPyTrue(pyString.__le__(otherPyString));
    }

    @Test
    public void test__le__PyUnicode() {
        PyUnicode otherPyUnicode = new PyUnicode(LESS_BEAUTIFUL);
        assertPyTrue(otherPyUnicode.__le__(pyString));
        assertPyFalse(pyString.__le__(otherPyUnicode));
        otherPyUnicode = new PyUnicode(BEAUTIFUL);
        assertPyTrue(otherPyUnicode.__le__(pyString));
        assertPyTrue(pyString.__le__(otherPyUnicode));
    }

    @Test
    public void test__gt__() {
        PyString otherPyString = new PyString(MORE_BEAUTIFUL);
        assertPyTrue(otherPyString.__gt__(pyString));
        assertPyFalse(pyString.__gt__(otherPyString));
    }

    @Test
    public void test__gt__PyUnicode() {
        PyUnicode otherPyUnicode = new PyUnicode(MORE_BEAUTIFUL);
        assertPyTrue(otherPyUnicode.__gt__(pyString));
        assertPyFalse(pyString.__gt__(otherPyUnicode));
    }

    @Test
    public void test__ge__() {
        PyString otherPyString = new PyString(MORE_BEAUTIFUL);
        assertPyTrue(otherPyString.__ge__(pyString));
        assertPyFalse(pyString.__ge__(otherPyString));
        otherPyString = new PyString(BEAUTIFUL);
        assertPyTrue(otherPyString.__ge__(pyString));
        assertPyTrue(pyString.__ge__(otherPyString));
    }

    @Test
    public void test__ge__PyUnicode() {
        PyUnicode otherPyUnicode = new PyUnicode(MORE_BEAUTIFUL);
        assertPyTrue(otherPyUnicode.__ge__(pyString));
        assertPyFalse(pyString.__ge__(otherPyUnicode));
        otherPyUnicode = new PyUnicode(BEAUTIFUL);
        assertPyTrue(otherPyUnicode.__ge__(pyString));
        assertPyTrue(pyString.__ge__(otherPyUnicode));
    }

    @Test
    public void test__unicode__() {
        assertPyTrue(pyString.__eq__(pyString.__unicode__()));
    }

    @Test
    public void test_ReplaceAll() {
        PyString result = pyString._replace(SMALL_O_UMLAUT, "oe", -1);
        assertEquals("schoen \n und \n toeier", result.getString());
    }

    @Test
    public void test_ReplaceNothing() {
        PyString result = pyString._replace(SMALL_O_UMLAUT, "oe", 0);
        assertEquals(BEAUTIFUL, result.getString());
    }

    @Test
    public void test_ReplaceOne() {
        PyString result = pyString._replace(SMALL_O_UMLAUT, "oe", 1);
        assertEquals("schoen \n und \n t\u00F6ier", result.getString());
    }

    @Test
    public void test_ReplaceTooMuch() {
        PyString result = pyString._replace(SMALL_O_UMLAUT, "oe", 4);
        assertEquals("schoen \n und \n toeier", result.getString());
    }

    @Test
    public void testReplacePyUnicode() {
        PyUnicode toBeReplaced = new PyUnicode(SMALL_O_UMLAUT);
        PyUnicode replacement = new PyUnicode("oe");
        PyString result = pyString.replace(toBeReplaced, replacement, -1);
        assertEquals("schoen \n und \n toeier", result.getString());
    }

    @Test
    public void testReplace() {
        PyString toBeReplaced = new PyString(SMALL_O_UMLAUT);
        PyString replacement = new PyString("oe");
        PyString result = pyString.replace(toBeReplaced, replacement, -1);
        assertEquals("schoen \n und \n toeier", result.getString());
    }

    @Test
    public void test__contains__() {
        PyString searchString = new PyString(SMALL_O_UMLAUT);
        assertTrue(pyString.__contains__(searchString));
    }

    @Test
    public void test__contains__PyUnicode() {
        PyUnicode searchString = new PyUnicode(SMALL_O_UMLAUT);
        assertTrue(pyString.__contains__(searchString));
    }

    @Test
    public void testStrip() {
        PyString toBeStripped = new PyString(SMALL_O_UMLAUT + SMALL_O_UMLAUT);
        String originalString = SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT //
                        + "GUGUS" //
                        + SMALL_O_UMLAUT + SMALL_O_UMLAUT;
        PyString originalPyString = new PyString(originalString);
        PyObject result = originalPyString.strip(toBeStripped);
        assertTrue(result instanceof PyString);
        assertEquals("GUGUS", ((PyString) result).getString());
    }

    @Test
    public void testStripPyUnicode() {
        PyUnicode toBeStripped = new PyUnicode(SMALL_O_UMLAUT + SMALL_O_UMLAUT);
        String originalString = SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT //
                        + "GUGUS" //
                        + SMALL_O_UMLAUT + SMALL_O_UMLAUT;
        PyString originalPyString = new PyString(originalString);
        PyObject result = originalPyString.strip(toBeStripped);
        assertTrue(result instanceof PyString);
        assertEquals("GUGUS", ((PyString) result).getString());
    }

    @Test
    public void testLstrip() {
        PyString toBeStripped = new PyString(SMALL_O_UMLAUT + SMALL_O_UMLAUT);
        String originalString = SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT //
                        + "GUGUS" //
                        + SMALL_O_UMLAUT + SMALL_O_UMLAUT;
        PyString originalPyString = new PyString(originalString);
        PyObject result = originalPyString.lstrip(toBeStripped);
        assertTrue(result instanceof PyString);
        assertEquals("GUGUS" + SMALL_O_UMLAUT + SMALL_O_UMLAUT, ((PyString) result).getString());
    }

    @Test
    public void testLstripPyUnicode() {
        PyUnicode toBeStripped = new PyUnicode(SMALL_O_UMLAUT + SMALL_O_UMLAUT);
        String originalString = SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT //
                        + "GUGUS" //
                        + SMALL_O_UMLAUT + SMALL_O_UMLAUT;
        PyString originalPyString = new PyString(originalString);
        PyObject result = originalPyString.lstrip(toBeStripped);
        assertTrue(result instanceof PyString);
        assertEquals("GUGUS" + SMALL_O_UMLAUT + SMALL_O_UMLAUT, ((PyString) result).getString());
    }

    @Test
    public void testRstrip() {
        PyString toBeStripped = new PyString(SMALL_O_UMLAUT + SMALL_O_UMLAUT);
        String originalString = SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT //
                        + "GUGUS" //
                        + SMALL_O_UMLAUT + SMALL_O_UMLAUT;
        PyString originalPyString = new PyString(originalString);
        PyObject result = originalPyString.rstrip(toBeStripped);
        assertTrue(result instanceof PyString);
        assertEquals(SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT + "GUGUS",
                        ((PyString) result).getString());
    }

    @Test
    public void testRstripPyUnicode() {
        PyUnicode toBeStripped = new PyUnicode(SMALL_O_UMLAUT + SMALL_O_UMLAUT);
        String originalString = SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT //
                        + "GUGUS" //
                        + SMALL_O_UMLAUT + SMALL_O_UMLAUT;
        PyString originalPyString = new PyString(originalString);
        PyObject result = originalPyString.rstrip(toBeStripped);
        assertTrue(result instanceof PyString);
        assertEquals(SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT + SMALL_O_UMLAUT + "GUGUS",
                        ((PyString) result).getString());
    }

    @Test
    public void testSplit() {
        PySystemState.initialize();
        PyList pyList = pyString.split(" ");
        assertEquals(5, pyList.__len__());
        assertEquals("sch\u00F6n", pyList.get(0));
        assertEquals("\n", pyList.get(1));
        assertEquals("und", pyList.get(2));
        assertEquals("\n", pyList.get(3));
        assertEquals("t\u00F6ier", pyList.get(4));
    }

    @Test
    public void testSplitSep() {
        PySystemState.initialize();
        PyString sep = new PyString(SMALL_O_UMLAUT);
        PyList pyList = pyString.split(sep);
        assertEquals(3, pyList.__len__());
        assertEquals("sch", pyList.get(0));
        assertEquals("n \n und \n t", pyList.get(1));
        assertEquals("ier", pyList.get(2));
    }

    @Test
    public void testSplitSepPyUnicode() {
        PySystemState.initialize();
        PyUnicode sep = new PyUnicode(SMALL_O_UMLAUT);
        PyList pyList = pyString.split(sep);
        assertEquals(3, pyList.__len__());
        assertEquals("sch", pyList.get(0));
        assertEquals("n \n und \n t", pyList.get(1));
        assertEquals("ier", pyList.get(2));
    }

    @Test
    public void testRsplit() {
        PySystemState.initialize();
        PyList pyList = pyString.rsplit(" ");
        assertEquals(5, pyList.__len__());
        assertEquals("sch\u00F6n", pyList.get(0));
        assertEquals("\n", pyList.get(1));
        assertEquals("und", pyList.get(2));
        assertEquals("\n", pyList.get(3));
        assertEquals("t\u00F6ier", pyList.get(4));
    }

    @Test
    public void testSRplitSep() {
        PySystemState.initialize();
        PyString sep = new PyString(SMALL_O_UMLAUT);
        PyList pyList = pyString.rsplit(sep);
        assertEquals(3, pyList.__len__());
        assertEquals("sch", pyList.get(0));
        assertEquals("n \n und \n t", pyList.get(1));
        assertEquals("ier", pyList.get(2));
    }

    @Test
    public void testRsplitSepPyUnicode() {
        PySystemState.initialize();
        PyUnicode sep = new PyUnicode(SMALL_O_UMLAUT);
        PyList pyList = pyString.rsplit(sep);
        assertEquals(3, pyList.__len__());
        assertEquals("sch", pyList.get(0));
        assertEquals("n \n und \n t", pyList.get(1));
        assertEquals("ier", pyList.get(2));
    }

    @Test
    public void testFind() {
        PyString toBeFound = new PyString(SMALL_O_UMLAUT);
        assertEquals(3, pyString.find(toBeFound));
    }

    @Test
    public void testFindPyUnicode() {
        PyUnicode toBeFound = new PyUnicode(SMALL_O_UMLAUT);
        assertEquals(3, pyString.find(toBeFound));
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
