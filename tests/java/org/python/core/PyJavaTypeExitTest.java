package org.python.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.python.util.PythonInterpreter;


class PyJavaTypeExitTest {

    @Test
    void testNoJavaLangSystemExitPossible() {
        PySystemState.initialize();
        try (PythonInterpreter interpreter = new PythonInterpreter()) {
            interpreter.exec(callJavaLangSystemExit());
            fail("AttributeError expected");
        } catch (PyException se) {
            assertEquals("AttributeError: type object 'java.lang.System' has no attribute 'exit'", se.getMessage());
        }
    }

    private String callJavaLangSystemExit() {
        StringBuilder b = new StringBuilder(100);
        b.append("from java.lang import System\n");
        b.append("System.exit(0)\n");
        return b.toString();
    }

}
