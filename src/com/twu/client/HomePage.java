package com.twu.client;

import com.twu.utility.PrintUtil;
import com.twu.utility.StdinService;
import com.twu.utility.StringUtil;


/**
 * The HomePage is displayed when the application has been launched for
 * the first time, or when the user has logged out.
 *
 * The HomePage will display a welcome message and ask the user for the
 * user type, either admin or regular user, or the user just wants to
 * select the "EXIT" option.
 *
 * When a user type has been selected, the HomePage will ask the user
 * for his/her username. In case of an admin user, the username has
 * to match an existing record in the list of admin users, and then
 * the user has to enter the correct password in order to log in. In
 * case of a regular user, if the username matches an existing record
 * in the list of regular users, user is logged in; otherwise a new
 * user will be created and the user is logged in automatically
 */
class HomePage {


    /**
     * The options to be displayed on the home page
     */
    private HomeOption[] homeOptions;


    /**
     * Initialize the HomePage by getting values from the HomeOption enum
     */
    HomePage() {
        homeOptions = HomeOption.values();
    }


    /**
     * Ask the user for the user type that he/she wants to access,
     * or if he/she wants to exit (but nothing will happen, then)
     *
     * @return the option that the user has selected
     */
    HomeOption askUserType() {

        StdinService stdinService = StdinService.getInstance();

        int input;

        PrintUtil.printMessage("欢迎来到热搜排行榜，请选择您的用户类型：");

        while (true) {

            PrintUtil.printOptions(homeOptions);
            PrintUtil.printLine(1);

            String in = stdinService.readLine();
            PrintUtil.printLine(1);

            if (StringUtil.isNaN(in)) {
                PrintUtil.printMessage("输入不合法，请重新选择您的用户类型：");
                continue;
            }

            input = Integer.parseInt(in);

            if (input < 1 || input > homeOptions.length) {
                PrintUtil.printMessage("输入不合法，请重新选择您的用户类型：");
                continue;
            }

            if (input == homeOptions.length){
                PrintUtil.printMessage("欢迎来到热搜排行榜，请选择您的用户类型：");
                continue;
            }

            break;
        }

        return homeOptions[input - 1];
    }

}
