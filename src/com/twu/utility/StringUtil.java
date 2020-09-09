package com.twu.utility;


/**
 * Utility methods for manipulating Strings
 */
public class StringUtil {


    /**
     * Check whether a String is numeric. Negative numbers
     * in this application are also considered as NaN
     *
     * @param str the string to be checked
     * @return whether the String is numeric
     */
    public static boolean isNaN(String str) {

        if (str == null || str.length() == 0)
            return true;

        for (char c : str.toCharArray())
            if (!Character.isDigit(c)) return true;

        return false;
    }


    /**
     * Do not let this class to be instantiated
     */
    private StringUtil() {}

}
