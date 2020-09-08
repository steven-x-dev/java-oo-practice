package com.twu.client;

import com.twu.utility.Feature;


/**
 * The options offered to the user on the user dashboard page
 * after the user has logged in
 */
public enum UserOption implements Option {


    SEE_HOT_SEARCHES     (Feature.SEE_HOT_SEARCHES,     "查看热搜排行榜"),

    ADD_HOT_SEARCH       (Feature.ADD_HOT_SEARCH,       "添加热搜"),

    ADD_SUPER_HOT_SEARCH (Feature.ADD_SUPER_HOT_SEARCH, "添加超级热搜"),

    VOTE_HOT_SEARCH      (Feature.VOTE_HOT_SEARCH,      "给热搜投票"),

    BUY_HOT_SEARCH       (Feature.BUY_HOT_SEARCH,       "购买热搜"),

    EXIT                 (Feature.EXIT,                 "退出");


    /**
     * Each option on the user dashboard page corresponds to
     * a Feature that might or might not be accessible to the
     * authenticated user
     */
    private final Feature FEATURE;


    private final String NAME;


    UserOption(Feature feature, String name) {
        FEATURE = feature;
        NAME = name;
    }


    public String getName() {
        return NAME;
    }


    public Feature getFeature() {
        return FEATURE;
    }

}
