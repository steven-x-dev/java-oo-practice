package com.twu.utility;


/**
 * The features include all the activities that the user may use,
 * and some are only accessible to certain user types
 */
public enum Feature {

    /**
     * See a full list of all hot searches
     */
    SEE_HOT_SEARCHES,

    /**
     * Add a hot search with a certain name that hasn't been added
     */
    ADD_HOT_SEARCH,

    /**
     * Add a super hot search, whose votes doubles when a user adds
     * votes to it. Only accessible to admin users
     */
    ADD_SUPER_HOT_SEARCH,

    /**
     * Add votes to a certain hot search that has been added
     */
    VOTE_HOT_SEARCH,

    /**
     * Buy a given position on the ranking list for a hot search
     * that has been added with a certain amount
     */
    BUY_HOT_SEARCH,

    /**
     * Log out the current user and return to the home page
     */
    EXIT

}
