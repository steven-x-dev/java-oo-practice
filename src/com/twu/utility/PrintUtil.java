package com.twu.utility;

import com.twu.client.Option;
import com.twu.entity.HotSearch;

import java.util.List;


/**
 * The PrintUtil prints a variety of data to stdout using a pre-defined format
 */
public class PrintUtil {


    /**
     * Print a list of hot searches into an ordered list with numbers starting from 1,
     * or a prompt message if hotSearches is null or empty
     *
     * @param hotSearches the list of hot searches to be printed
     */
    public static void printHotSearches(List<HotSearch> hotSearches) {

        if (hotSearches == null || hotSearches.size() == 0) {
            System.out.println("热搜列表为空");
            return;
        }

        for (int i = 0; i < hotSearches.size(); i++)
            System.out.println(String.format("%d. %s", i + 1, hotSearches.get(i).toString()));
    }


    /**
     * Print a list of options into an ordered list with numbers starting from 1 so that
     * the user can input the number to access features he wants, or a prompt message if
     * options is null or empty
     *
     * @param options the list of options to be printed
     */
    public static void printOptions(Option[] options) {

        if (options == null || options.length == 0) {
            System.out.println("当前选项列表为空");
            return;
        }

        for (int i = 0; i < options.length; i++)
            System.out.println(String.format("%d. %s", i + 1, options[i].getName()));
    }


    /**
     * Print a message to a line
     *
     * @param message the printed message
     */
    public static void printMessage(String message) {
        System.out.println(message);
    }


    /**
     * Print a given number of empty lines
     *
     * @param n the number of empty lines
     */
    public static void printLine(int n) {
        for (int i = 0; i < n; i++)
            System.out.println();
    }


    /**
     * Do not let this class to be instantiated
     */
    private PrintUtil() {}

}
