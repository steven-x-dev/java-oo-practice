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

            if (StringUtil.isNaN(str)) {
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
     * Do not let this class to be instantiated
     */
    private InteractionUtil() {}

}
