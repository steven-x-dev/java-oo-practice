package com.twu.client;

import com.twu.utility.InteractionUtil;


/**
 * The HomePage is displayed when the application has been launched for
 * the first time, or when the user has logged out.
 *
 * The HomePage will display a welcome message and ask the user for the
 * user type, either admin or regular user, or if the user just wants to
 * select the "EXIT" option.
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

        String title = "欢迎来到热搜排行榜，请选择您的用户类型：";
        String optionName = "您的用户类型";

        HomeOption option = InteractionUtil.getCorrectOption(homeOptions, title, optionName);

        while (option == HomeOption.EXIT)
            option = InteractionUtil.getCorrectOption(homeOptions, title, optionName);

        return option;
    }

}
