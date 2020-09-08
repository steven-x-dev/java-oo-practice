package com.twu.utility;


/**
 * Utility methods for manipulating Strings
 */
public class StringUtil {


    /**
     * Check whether a String is numeric
     *
     * @param str the string to be checked
     * @return whether the String is numeric
     */
    public static boolean isNaN(String str) {

        for (char c : str.toCharArray())
            if (!Character.isDigit(c)) return true;

        return false;
    }


    /**
     * Do not let this class to be instantiated
     */
    private StringUtil() {}

}
