/*
 * Copyright 2000 Finn Bock
 *
 * This program contains material copyrighted by:
 * Copyright (c) 1997-2000 by Secret Labs AB.  All rights reserved.
 *
 * This version of the SRE library can be redistributed under CNRI's
 * Python 1.6 license.  For any other use, please contact Secret Labs
 * AB (info@pythonware.com).
 *
 * Portions of this engine have been developed in cooperation with
 * CNRI.  Hewlett-Packard provided funding for 1.6 integration and
 * other compatibility work.
 */
package org.python.modules;

import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.modules.sre.PatternObject;
import org.python.modules.sre.SRE_STATE;

/**
 * _sre
 */
public class _sre {
    /** MAGIC */
    public static int MAGIC = SRE_STATE.SRE_MAGIC;

    /** probably the right number for Jython since we are UTF-16. */
    public static int MAXREPEAT = 65535;
 
    /**
     * workaround the fact that H, I types are unsigned, but we are not really using them as such <br>
     * XXX: May not be the right size, but I suspect it is -- see sre_compile.py
     */
    public static int CODESIZE = 4;

    /** Default constructor */
    _sre() {
    }

    /**
     * Compile
     * 
     * @param pattern
     *            pattern
     * @param flags
     *            flags
     * @param code
     *            code
     * @param groups
     *            groups
     * @param groupindex
     *            groupindex
     * @param indexgroup
     *            indexgroup
     * @return o
     */
    public static PatternObject compile(PyString pattern, int flags, PyObject code, int groups,
                                        PyObject groupindex, PyObject indexgroup) {
        int[] ccode = new int[code.__len__()];
        int i = 0;
        for (PyObject item : code.asIterable()) {
            ccode[i++] = (int)item.asLong();
        }
        return new PatternObject(pattern, flags, ccode, groups, groupindex, indexgroup);
    }

    /**
     * Getcodesize
     * 
     * @return i
     */
    public static int getcodesize() {
        return CODESIZE;
    }

    /**
     * Getlower
     * 
     * @param ch
     *            ch
     * @param flags
     *            flags
     * @return i
     */
    public static int getlower(int ch, int flags) {
        return SRE_STATE.getlower(ch, flags);
    }
}
