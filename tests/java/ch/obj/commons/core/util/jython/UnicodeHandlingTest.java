package ch.obj.commons.core.util.jython;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

public class UnicodeHandlingTest {

    private static final String BEAUTIFUL = "sch\u00F6n";

    @Test
    public void testStr() {
        PySystemState.initialize();
        try (PythonInterpreter interpreter = new PythonInterpreter()) {
            interpreter.set("bv", BEAUTIFUL);
            interpreter.exec(strScript());

            PyObject res = interpreter.get("result");
            assertNotNull(res);
            assertTrue(res instanceof PyString);
            assertEquals(BEAUTIFUL, ((PyString) res).getString());
        }
    }

    private String strScript() {
        StringBuilder b = new StringBuilder();
        b.append("result = str(bv)\n");
        return b.toString();
    }

    @Test
    public void testAssign() {
        PySystemState.initialize();
        try (PythonInterpreter interpreter = new PythonInterpreter()) {
            interpreter.set("bv", BEAUTIFUL);
            interpreter.exec(assignScript());

            PyObject res = interpreter.get("result");
            assertNotNull(res);
            assertFalse("result should not be 'initial'", "initial".equals(res.toString()));
        }
    }

    private String assignScript() {
        StringBuilder b = new StringBuilder();
        b.append("result = 'initial'\n");
        b.append("if bv == 'schön':\n");
        b.append("  result = 'schön'\n");
        return b.toString();
    }

    @Test
    public void testReplace() {
        PySystemState.initialize();
        try (PythonInterpreter interpreter = new PythonInterpreter()) {
            interpreter.set("bv", BEAUTIFUL);
            interpreter.exec(replaceScript());

            PyObject res = interpreter.get("result");
            assertNotNull(res);
            assertEquals("Expect umlaut to be replaced", "schoen", res.toString());
        }
    }

    private String replaceScript() {
        StringBuilder b = new StringBuilder();
        b.append("umlauts = ['ä', 'Ä', 'ü', 'Ü', 'ö', 'Ö']\n");
        b.append("replacements = ['ae', 'Ae', 'ue', 'Ue', 'oe', 'Oe']\n");
        b.append("for i in range(len(umlauts)):\n");
        b.append("  bv = bv.replace(umlauts[i], replacements[i])\n");
        b.append("result = bv\n");
        return b.toString();
    }

}