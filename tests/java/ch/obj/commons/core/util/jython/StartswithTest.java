package ch.obj.commons.core.util.jython;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.python.core.PyBoolean;
import org.python.util.PythonInterpreter;

class StartswithTest {

    // variable name
    private static final String RESULT = "result";

    private String umlautStartswithUmlaut() {
        StringBuffer b = new StringBuffer();
        b.append("from ch.obj.commons.core.util.jython import JavaStringProvider\n");
        b.append("value = JavaStringProvider.getBeautiful()\n");
        b.append("start = JavaStringProvider.getStartOfBeautiful()\n");
        b.append("result = value.startswith(start)\n");
        return b.toString();
    }

    @Test
    void testUmlautStartswithUmlaut() {
        try (PythonInterpreter interpreter = new PythonInterpreter()) {
            interpreter.exec(umlautStartswithUmlaut());
            assertResultBoolean(true, interpreter);
        }
    }

    private String mixedUnicodeStartswithUmlaut() {
        StringBuffer b = new StringBuffer();
        b.append("from ch.obj.commons.core.util.jython import JavaStringProvider\n");
        b.append("value = JavaStringProvider.getBeautifulJeanneDArc()\n");
        b.append("start = JavaStringProvider.getBeautiful()\n");
        b.append("result = value.startswith(start)\n");
        return b.toString();
    }

    @Test
    void testMixedUnicodeStartswithUmlaut() {
        try (PythonInterpreter interpreter = new PythonInterpreter()) {
            interpreter.exec(mixedUnicodeStartswithUmlaut());
            assertResultBoolean(true, interpreter);
        }
    }

    private String unicodeStartswithUnicode() {
        StringBuffer b = new StringBuffer();
        b.append("from ch.obj.commons.core.util.jython import JavaStringProvider\n");
        b.append("value = JavaStringProvider.getJeanneDArc()\n");
        b.append("start = JavaStringProvider.getStartOfJeanneDArc()\n");
        b.append("result = value.startswith(start)\n");
        return b.toString();
    }

    @Test
    void testUnicodeStartswithUnicode() {
        try (PythonInterpreter interpreter = new PythonInterpreter()) {
            interpreter.exec(unicodeStartswithUnicode());
            assertResultBoolean(true, interpreter);
        }
    }

    private String umlautStartswithUnicode() {
        StringBuffer b = new StringBuffer();
        b.append("from ch.obj.commons.core.util.jython import JavaStringProvider\n");
        b.append("value = JavaStringProvider.getBeautiful()\n");
        b.append("start = JavaStringProvider.getStartOfJeanneDArc()\n");
        b.append("result = value.startswith(start)\n");
        return b.toString();
    }

    @Test
    void testUmlautStartswithUnicode() {
        try (PythonInterpreter interpreter = new PythonInterpreter()) {
            interpreter.exec(umlautStartswithUnicode());
            assertResultBoolean(false, interpreter);
        }
    }

    private String unicodeStartswithUmlaut() {
        StringBuffer b = new StringBuffer();
        b.append("from ch.obj.commons.core.util.jython import JavaStringProvider\n");
        b.append("value = JavaStringProvider.getJeanneDArc()\n");
        b.append("start = JavaStringProvider.getStartOfBeautiful()\n");
        b.append("result = value.startswith(start)\n");
        return b.toString();
    }

    @Test
    void testUnicodeStartswithUmlaut() {
        try (PythonInterpreter interpreter = new PythonInterpreter()) {
            interpreter.exec(unicodeStartswithUmlaut());
            assertResultBoolean(false, interpreter);
        }
    }

    private void assertResultBoolean(boolean expected, PythonInterpreter interpreter) {
        Object resultObject = interpreter.get(RESULT);
        if (resultObject instanceof PyBoolean) {
            PyBoolean result = (PyBoolean) resultObject;
            if (expected) {
                assertTrue(result.getBooleanValue(), "result:");
            } else {
                assertFalse(result.getBooleanValue(), "result:");
            }
        } else {
            fail("expected result to be Boolean but was " + resultObject.getClass().getName());
        }
    }

}
