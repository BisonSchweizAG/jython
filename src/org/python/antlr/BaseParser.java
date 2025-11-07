package org.python.antlr;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.python.antlr.base.mod;
import org.python.core.CodeFlag;
import org.python.core.CompilerFlags;

/**
 * BaseParser
 */
public class BaseParser {

    /** charStream */
    protected final CharStream charStream;
    /** partial */
    @Deprecated
    protected final boolean partial;
    /** printFunction, unicodeLiterals */
    protected final boolean printFunction, unicodeLiterals;
    /** filename */
    protected final String filename;
    /** encoding */
    protected final String encoding;
    /** errorHandler */
    protected ErrorHandler errorHandler = new FailFastHandler();
    
    /**
     * Constructor
     * 
     * @param stream
     *            stream
     * @param filename
     *            filename
     * @param flags
     *            flags
     */
    public BaseParser(CharStream stream, String filename, CompilerFlags flags) {
        this(stream, filename, flags.encoding, false,
             flags.isFlagSet(CodeFlag.CO_FUTURE_PRINT_FUNCTION),
             flags.isFlagSet(CodeFlag.CO_FUTURE_UNICODE_LITERALS));
    }

    /**
     * Constructor
     * 
     * @param stream
     *            stream
     * @param filename
     *            filename
     * @param encoding
     *            encoding
     */
    public BaseParser(CharStream stream, String filename, String encoding) {
        this(stream, filename, encoding, false, false, false);
    }

    /**
     * Constructor
     * 
     * @param stream
     *            stream
     * @param filename
     *            filename
     * @param encoding
     *            encodeing
     * @param partial
     *            partial
     */
    @Deprecated
    public BaseParser(CharStream stream, String filename, String encoding, boolean partial) {
        this(stream, filename, encoding, partial, false, false);
    }

    private BaseParser(CharStream stream, String filename, String encoding,
                       boolean partial, boolean printFunction, boolean unicodeLiterals) {
        this.charStream = stream;
        this.filename = filename;
        this.encoding = encoding;
        this.partial = partial;
        this.printFunction = printFunction;
        this.unicodeLiterals = unicodeLiterals;
    }

    /**
     * SetAntlrErrorHandler
     * 
     * @param eh
     *            eh
     */
    public void setAntlrErrorHandler(ErrorHandler eh) {
        this.errorHandler = eh;
    }

    /**
     * SetupParser
     * 
     * @param single
     *            single
     * @return parser
     */
    protected PythonParser setupParser(boolean single) {
        PythonLexer lexer = new PythonLexer(charStream);
        lexer.setErrorHandler(errorHandler);
        lexer.single = single;
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PythonTokenSource indentedSource = new PythonTokenSource(tokens, filename, single);
        tokens = new CommonTokenStream(indentedSource);
        PythonParser parser = new PythonParser(tokens, encoding, printFunction, unicodeLiterals);
        parser.setErrorHandler(errorHandler);
        parser.setTreeAdaptor(new PythonTreeAdaptor());
        return parser;
    }

    /**
     * ParseExpression
     * 
     * @return mod
     */
    public mod parseExpression() {
        mod tree = null;
        PythonParser parser = setupParser(false);
        try {
            PythonParser.eval_input_return r = parser.eval_input();
            tree = (mod)r.tree;
        } catch (RecognitionException e) {
            //XXX: this can't happen.  Need to strip the throws from antlr
            //     generated code.
        }
        return tree;
    }

    /**
     * ParseInteractive
     * 
     * @return mod
     */
    public mod parseInteractive() {
        mod tree = null;
        PythonParser parser = setupParser(true);
        try {
            PythonParser.single_input_return r = parser.single_input();
            tree = (mod)r.tree;
        } catch (RecognitionException e) {
            //I am only throwing ParseExceptions, but "throws RecognitionException" still gets
            //into the generated code.
            System.err.println("FIXME: pretty sure this can't happen -- but needs to be checked");
        }
        return tree;
    }

    /**
     * ParseModule
     * 
     * @return mod
     */
    public mod parseModule() {
        mod tree = null;
        PythonParser parser = setupParser(false);
        try {
            PythonParser.file_input_return r = parser.file_input();
            tree = (mod)r.tree;
        } catch (RecognitionException e) {
            //XXX: this can't happen.  Need to strip the throws from antlr
            //     generated code.
        }
        return tree;
    }
}
