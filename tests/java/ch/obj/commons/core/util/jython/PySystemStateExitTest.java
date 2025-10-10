package ch.obj.commons.core.util.jython;

import org.python.core.PyException;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

import junit.framework.TestCase;

public class PySystemStateExitTest extends TestCase {

    public void testNoExitPossible() {
        PySystemState.initialize();
        try (PythonInterpreter interpreter = new PythonInterpreter()) {
            interpreter.exec(callSysExit());
            fail("SyntaxError expected");
        } catch (PyException se) {
            assertEquals("SyntaxError: exit() not allowed in embedded mode", se.getMessage());
        }
    }

    private String callSysExit() {
        StringBuilder b = new StringBuilder(100);
        b.append("import sys\n");
        b.append("sys.exit()\n");
        return b.toString();
    }

}
