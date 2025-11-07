// (c) 2019 Jython Developers
// Licensed to PSF under a contributor agreement

package org.python.modules._locale;

import org.python.core.PyDictionary;
import org.python.core.PyString;

/**
 * Definition of a Python native locale implementation. Based on Python locale module behaviour and
 * implicit dependencies, and the {@code _localemodule.c} implementation in CPython.
 *
 * It is recommended classes implementing this interface are made immutable.
 *
 * @since Jython 2.7.2
 */
public interface PyLocale extends DateSymbolLocale {

    /**
     * Localeconv
     * 
     * @return dict
     */
    public PyDictionary localeconv();

    /**
     * GetLocaleString
     * 
     * @return s
     */
    public PyString getLocaleString();

    /**
     * GetUnderlyingLocale
     * 
     * @return s
     */
    public PyString getUnderlyingLocale();

    /**
     * Strcoll
     * 
     * @param str1
     *            str1
     * @param str2
     *            str2
     * @return i
     */
    public int strcoll(PyString str1, PyString str2);

    /**
     * Strxfrm
     * 
     * @param str1
     *            str1
     * @return s
     */
    public PyString strxfrm(PyString str1);
}
