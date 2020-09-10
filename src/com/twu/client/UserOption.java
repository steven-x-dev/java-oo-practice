package com.twu.client;


/**
 * The options offered to the user on the user dashboard page
 * after the user has logged in
 */
public enum UserOption implements Option {


    SEE_HOT_SEARCHES     ("查看热搜排行榜"),

    ADD_HOT_SEARCH       ("添加热搜"),

    ADD_SUPER_HOT_SEARCH ("添加超级热搜"),

    VOTE_HOT_SEARCH      ("给热搜投票"),

    BUY_HOT_SEARCH       ("购买热搜"),

    EXIT                 ("退出");


    private final String NAME;


    UserOption(String name) {
        NAME = name;
    }


    public String getName() {
        return NAME;
    }

}
