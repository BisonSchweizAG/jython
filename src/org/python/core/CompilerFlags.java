// At some future point this will also be extended - in conjunction with
// Py#compileFlags - to add
// support for a compiler factory that user code can choose in place of the
// normal compiler.
// (Perhaps a better name might have been "CompilerOptions".)

package org.python.core;

import java.io.Serializable;
import java.util.Set;

import org.python.Version;

/**
 * CompilerFlags
 */
public class CompilerFlags implements Serializable {
    // These flags don't mean anything to the code, only to the compiler
    /** PyCF_SOURCE_IS_UTF8 */
    public static final int PyCF_SOURCE_IS_UTF8 = 0x0100;
    /** PyCF_DONT_IMPLY_DEDENT */
    public static final int PyCF_DONT_IMPLY_DEDENT = 0x0200;
    /** PyCF_ONLY_AST */
    public static final int PyCF_ONLY_AST = 0x0400;
    /** only_ast */
    public boolean only_ast;
    /** dont_imply_dedent */
    public boolean dont_imply_dedent;
    /** source_is_utf8 */
    public boolean source_is_utf8;
    /** encoding */
    public String encoding;

    private final Set<CodeFlag> flags = Version.getDefaultCodeFlags();

    /** Default constructor */
    public CompilerFlags() {
    }

    /**
     * Constructor
     * 
     * @param co_flags
     *            co_flags
     */
    public CompilerFlags(int co_flags) {
        for (CodeFlag flag : CodeFlag.parse(co_flags)) {
            setFlag(flag);
        }
        only_ast = isEnabled(co_flags, PyCF_ONLY_AST);
        dont_imply_dedent = isEnabled(co_flags, PyCF_DONT_IMPLY_DEDENT);
        source_is_utf8 = isEnabled(co_flags, PyCF_SOURCE_IS_UTF8);
    }

    private boolean isEnabled(int co_flags, int codeConstant) {
        return (co_flags & codeConstant) != 0;
    }

    /**
     * ToBits
     * 
     * @return i
     */
    public int toBits() {
        int bits = (only_ast ? PyCF_ONLY_AST : 0)
                | (dont_imply_dedent ? PyCF_DONT_IMPLY_DEDENT : 0)
                | (source_is_utf8 ? PyCF_SOURCE_IS_UTF8 : 0);
        for (CodeFlag flag : flags) {
            bits |= flag.flag;
        }
        return bits;
    }

    /**
     * SetFlag
     * 
     * @param flag
     *            flag
     */
    public void setFlag(CodeFlag flag) {
        flags.add(flag);
    }

    /**
     * IsFlagSet
     * 
     * @param flag
     *            flag
     * @return isSet
     */
    public boolean isFlagSet(CodeFlag flag) {
        return flags.contains(flag);
    }

    @Override
    public String toString() {
        return String.format(
                "CompilerFlags[division=%s nested_scopes=%s generators=%s "
                        + "with_statement=%s absolute_import=%s "
                        + "print_function=%s unicode_literals=%s only_ast=%s "
                        + "dont_imply_dedent=%s  source_is_utf8=%s]",
                isFlagSet(CodeFlag.CO_FUTURE_DIVISION),
                isFlagSet(CodeFlag.CO_NESTED),
                isFlagSet(CodeFlag.CO_GENERATOR_ALLOWED),
                isFlagSet(CodeFlag.CO_FUTURE_WITH_STATEMENT),
                isFlagSet(CodeFlag.CO_FUTURE_ABSOLUTE_IMPORT),
                isFlagSet(CodeFlag.CO_FUTURE_PRINT_FUNCTION),
                isFlagSet(CodeFlag.CO_FUTURE_UNICODE_LITERALS),
                only_ast,
                dont_imply_dedent,
                source_is_utf8);
    }

    /**
     * GetCompilerFlags
     * 
     * @return flags
     */
    public static CompilerFlags getCompilerFlags() {
        return getCompilerFlags(0, null);
    }

    private static final int CO_ALL_FEATURES = CompilerFlags.PyCF_DONT_IMPLY_DEDENT
            | CompilerFlags.PyCF_ONLY_AST
            | CompilerFlags.PyCF_SOURCE_IS_UTF8
            | CodeFlag.CO_NESTED.flag
            | CodeFlag.CO_GENERATOR_ALLOWED.flag
            | CodeFlag.CO_FUTURE_DIVISION.flag
            | CodeFlag.CO_FUTURE_ABSOLUTE_IMPORT.flag
            | CodeFlag.CO_FUTURE_WITH_STATEMENT.flag
            | CodeFlag.CO_FUTURE_PRINT_FUNCTION.flag
            | CodeFlag.CO_FUTURE_UNICODE_LITERALS.flag;

    /**
     * GetCompilerFlags
     * 
     * @param flags
     *            flags
     * @param frame
     *            frame
     * @return flags
     */
    public static CompilerFlags getCompilerFlags(int flags, PyFrame frame) {
        if ((flags & ~CO_ALL_FEATURES) != 0) {
            throw Py.ValueError("compile(): unrecognised flags");
        }
        return getCompilerFlags(new CompilerFlags(flags), frame);
    }

    /**
     * GetCompilerFlags
     * 
     * @param flags
     *            flags
     * @param frame
     *            frame
     * @return flags
     */
    public static CompilerFlags getCompilerFlags(CompilerFlags flags,
            PyFrame frame) {
        if (frame != null && frame.f_code != null) {
            return frame.f_code.co_flags.combine(flags);
        } else {
            return flags;
        }
    }

    /**
     * Combine<br>
     * this will not strictly be an OR once we have other options, like a compiler factory in that case, we would assume
     * 
     * @param flags
     *            flags
     * @return flags
     */
    public CompilerFlags combine(CompilerFlags flags) {
        return new CompilerFlags(this.toBits() | flags.toBits());
    }

    /**
     * Combine
     * 
     * @param flags
     *            flags
     * @return flags
     */
    public CompilerFlags combine(int flags) {
        return new CompilerFlags(this.toBits() | flags);
    }
}
