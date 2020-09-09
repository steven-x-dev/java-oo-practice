package com.twu.utility;

import com.twu.client.Option;


/**
 * A utility responsible for prompting the user for input, and reading the user input from stdin,
 * and validating the user input
 */
public class InteractionUtil {


    /**
     * 
     * @param options the options available to the user
     * @param title the initial message displayed above the list of options, used to prompt the user
     * @param optionName a general description of what the options are, displayed above the list of
     *                   options used to prompt the user, after the user has entered an invalid input
     * @param <T> the type of the Option
     * @return the selected option by the user
     */
    public static <T extends Option> T getCorrectOption(T[] options, String title, String optionName) {

        System.out.println(title);

        if (options == null || options.length == 0) {
            System.out.println("当前选项列表为空");
            return null;
        }

        String msg = String.format("输入不合法，请重新选择%s：", optionName);

        while (true) {

            for (int i = 0; i < options.length; i++)
                System.out.println(String.format("%d. %s", i + 1, options[i].getName()));
            System.out.println();

            String str = StdinService.getInstance().readLine();
            System.out.println();

            if (isNaN(str)) {
                System.out.println(msg);
                continue;
            }

            int number = Integer.parseInt(str);

            if (number < 1 || number > options.length) {
                System.out.println(msg);
                continue;
            }

            return options[number - 1];
        }
    }


    /**
     * Get a non-empty String from the next line in stdin
     *
     * @param emptyMessage the message to display if the next line is empty
     * @return the next line in stdin, or null if it's empty
     */
    public static String getNonEmptyString(String emptyMessage) {

        String str = StdinService.getInstance().readLine();
        System.out.println();

        if (str.length() < 1) {
            System.out.println(emptyMessage);
            System.out.println();
            return null;
        }

        return str;
    }


    /**
     * Get a positive integer from the next line in stdin. If the user input is
     * invalid, the loop doesn't stop until the the user inputs a correct value
     *
     * @param integerName a general description of what the integer is, displayed after
     *                    the user has entered an invalid input to prompt the user
     * @return a guaranteed positive integer
     */
    public static int getPositiveInteger(String integerName) {

        String msg = String.format("输入不合法，请重新输入%s：", integerName);

        while (true) {

            String str = StdinService.getInstance().readLine();
            System.out.println();

            if (isNaN(str)) {
                System.out.println(msg);
                System.out.println();
                continue;
            }

            int integer = Integer.parseInt(str);

            if (integer < 1) {
                System.out.println(msg);
                System.out.println();
                continue;
            }

            return integer;
        }
    }


    /**
     * Check whether a String is numeric. Negative numbers
     * in this application are also considered as NaN
     *
     * @param str the string to be checked
     * @return whether the String is numeric
     */
    private static boolean isNaN(String str) {

        if (str == null || str.length() == 0)
            return true;

        for (char c : str.toCharArray())
            if (!Character.isDigit(c)) return true;

        return false;
    }


    /**
     * Do not let this class to be instantiated
     */
    private InteractionUtil() {}

}
