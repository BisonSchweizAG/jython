package org.python.compiler;

import java.io.OutputStream;

import org.python.antlr.base.mod;
import org.python.core.CompilerFlags;

/**
 * Helper class allowing to call compile() without a dependency to asm
 */
public class ModuleHelper {

    private ModuleHelper() {
    }

    public static void compile(mod node, OutputStream ostream, String name, String filename, boolean linenumbers,
                    boolean printResults, CompilerFlags cflags) throws Exception {
        Module.compile(node, ostream, name, filename, linenumbers, printResults, cflags);
    }

}
