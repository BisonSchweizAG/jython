package org.python.core;

import org.python.antlr.BaseParser;
import org.python.antlr.base.mod;

/**
 * CompileMode
 */
public enum CompileMode {
    /** eval */
    eval {
        @Override
        mod dispatch(BaseParser parser) {
            return parser.parseExpression();
        }
    },
    /** single */
    single {
        @Override
        mod dispatch(BaseParser parser) {
            return parser.parseInteractive();
        }
    },
    /** exec */
    exec {
        @Override
        mod dispatch(BaseParser parser) {
            return parser.parseModule();
        }
    };

    /**
     * Dispatch
     * 
     * @param parser
     *            parser
     * @return mod
     */
    abstract mod dispatch(BaseParser parser);

    /**
     * GetMode
     * 
     * @param mode
     *            mode
     * @return mode
     */
    public static CompileMode getMode(String mode) {
        if (!mode.equals("exec") && !mode.equals("eval") && !mode.equals("single")) {
            throw Py.ValueError("compile() arg 3 must be 'exec' or 'eval' or 'single'");
        }
        return valueOf(mode);
    }
}
