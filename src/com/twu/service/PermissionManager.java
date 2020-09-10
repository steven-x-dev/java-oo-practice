package com.twu.service;

import com.twu.client.UserOption;
import com.twu.entity.AdminUser;
import com.twu.entity.RegularUser;
import com.twu.entity.User;

import java.util.function.Predicate;

import static com.twu.service.PermissionManager.Feature.*;


/**
 * The PermissionManager is a singleton that simulates the functionality of a
 * permission table that grants access to certain features to a given user type
 */
public class PermissionManager {


    /**
     * The singleton instance
     */
    private static PermissionManager instance = new PermissionManager();


    /**
     * Returns a predicate that checks whether the user option is accessible
     * to the current user
     *
     * @param user the accessible features of the current user
     * @return the predicate that checks whether the option is accessible to the current user
     */
    public Predicate<UserOption> canAccessFeature(User user) {

        Feature[] accessibleFeatures = grantAccess(user.getClass());

        return userOption -> {

            for (Feature feature : accessibleFeatures)
                if (feature.toString().equals(userOption.toString())) return true;

            return false;
        };
    }


    /**
     * Grant the user access to certain features based on the user's Class
     *
     * @param userClass the Class of the user entity
     * @return the features that are accessible to the Class of the user
     */
    private Feature[] grantAccess(Class userClass) {

        if (userClass == AdminUser.class)
            return new Feature[] { SEE_HOT_SEARCHES, ADD_HOT_SEARCH, ADD_SUPER_HOT_SEARCH, EXIT };

        if (userClass == RegularUser.class)
            return new Feature[] { SEE_HOT_SEARCHES, ADD_HOT_SEARCH, VOTE_HOT_SEARCH, BUY_HOT_SEARCH, EXIT };

        throw new IllegalArgumentException("user type not supported");
    }


    /**
     * Do not let this class to be instantiated externally
     */
    private PermissionManager() {}


    /**
     * Return the singleton instance of PermissionManager
     */
    public static PermissionManager getInstance() {
        return instance;
    }


    /**
     * The features include all the activities that the user may use,
     * and some are only accessible to certain user types
     */
    enum Feature {

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

}
