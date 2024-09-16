package ch.obj.commons.core.util.jython;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.python.core.PyBoolean;
import org.python.util.PythonInterpreter;

public class EndswithTest {

    // variable name
    private static final String RESULT = "result";

    private String umlautEndswithUmlaut() {
        StringBuffer b = new StringBuffer();
        b.append("from ch.obj.commons.core.util.jython import JavaStringProvider\n");
        b.append("value = JavaStringProvider.getBeautiful()\n");
        b.append("end = JavaStringProvider.getEndOfBeautiful()\n");
        b.append("result = value.endswith(end)\n");
        return b.toString();
    }

    @Test
    public void testUmlautEndswithUmlaut() {
        try (PythonInterpreter interpreter = new PythonInterpreter()) {
            interpreter.exec(umlautEndswithUmlaut());
            assertResultBoolean(true, interpreter);
        }
    }

    private String unicodeEndswithUnicode() {
        StringBuffer b = new StringBuffer();
        b.append("from ch.obj.commons.core.util.jython import JavaStringProvider\n");
        b.append("value = JavaStringProvider.getJeanneDArc()\n");
        b.append("end = JavaStringProvider.getEndOfJeanneDArc()\n");
        b.append("result = value.endswith(end)\n");
        return b.toString();
    }

    @Test
    public void testUnicodeEndswithUnicode() {
        try (PythonInterpreter interpreter = new PythonInterpreter()) {
            interpreter.exec(unicodeEndswithUnicode());
            assertResultBoolean(true, interpreter);
        }
    }

    private String umlautEndswithUnicode() {
        StringBuffer b = new StringBuffer();
        b.append("from ch.obj.commons.core.util.jython import JavaStringProvider\n");
        b.append("value = JavaStringProvider.getBeautiful()\n");
        b.append("end = JavaStringProvider.getEndOfJeanneDArc()\n");
        b.append("result = value.endswith(end)\n");
        return b.toString();
    }

    @Test
    public void testUmlautEndswithUnicode() {
        try (PythonInterpreter interpreter = new PythonInterpreter()) {
            interpreter.exec(umlautEndswithUnicode());
            assertResultBoolean(false, interpreter);
        }
    }

    private String unicodeEndswithUmlaut() {
        StringBuffer b = new StringBuffer();
        b.append("from ch.obj.commons.core.util.jython import JavaStringProvider\n");
        b.append("value = JavaStringProvider.getJeanneDArc()\n");
        b.append("end = JavaStringProvider.getEndOfBeautiful()\n");
        b.append("result = value.endswith(end)\n");
        return b.toString();
    }

    @Test
    public void testUnicodeEndswithUmlaut() {
        try (PythonInterpreter interpreter = new PythonInterpreter()) {
            interpreter.exec(unicodeEndswithUmlaut());
            assertResultBoolean(false, interpreter);
        }
    }

    private void assertResultBoolean(boolean expected, PythonInterpreter interpreter) {
        Object resultObject = interpreter.get(RESULT);
        if (resultObject instanceof PyBoolean) {
            PyBoolean result = (PyBoolean) resultObject;
            if (expected) {
                assertTrue("result:", result.getBooleanValue());
            } else {
                assertFalse("result:", result.getBooleanValue());
            }
        } else {
            fail("expected result to be Boolean but was " + resultObject.getClass().getName());
        }
    }

}
