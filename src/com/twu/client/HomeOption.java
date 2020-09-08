package com.twu.client;


/**
 * The options offered to the user on the home page
 */
public enum HomeOption implements Option {


    ADMIN_USER   ("管理员"),

    REGULAR_USER ("普通用户"),

    EXIT         ("退出");


    private final String NAME;


    HomeOption(String name) {
        NAME = name;
    }


    public String getName() {
        return NAME;
    }

}
