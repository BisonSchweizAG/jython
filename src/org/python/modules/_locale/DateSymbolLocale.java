package org.python.modules._locale;

/**
 * Date related string values.
 *
 * @since Jython 2.7.2
 */
public interface DateSymbolLocale {

    /**
     * GetShortWeekdays
     * 
     * @return days
     */
    public String[] getShortWeekdays();

    /**
     * GetShortMonths
     * 
     * @return months
     */
    public String[] getShortMonths();

    /**
     * GetMonths
     * 
     * @return months
     */
    public String[] getMonths();

    /**
     * GetAmPmStrings
     * 
     * @return strings
     */
    public String[] getAmPmStrings();

    /**
     * GetWeekdays
     * 
     * @return week days
     */
    public String[] getWeekdays();

}