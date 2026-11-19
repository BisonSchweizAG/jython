package org.python.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.python.util.PythonInterpreter;


class PySystemStateExitTest {

    @Test
    void testNoExitPossible() {
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
