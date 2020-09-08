package com.twu.utility;

import com.twu.entity.AdminUser;
import com.twu.entity.RegularUser;

import static com.twu.utility.Feature.*;


/**
 * Simulate the functionality of a permission table that grants access
 * to certain features to a given user type
 */
public class PermissionManager {


    /**
     * Grant the user access to certain features based on the user's Class
     *
     * @param userClass the Class of the user entity
     * @return the features that are accessible to the Class of the user
     */
    public static Feature[] grantAccess(Class userClass) {

        if (userClass == AdminUser.class)
            return new Feature[] { SEE_HOT_SEARCHES, ADD_HOT_SEARCH, ADD_SUPER_HOT_SEARCH, EXIT };

        if (userClass == RegularUser.class)
            return new Feature[] { SEE_HOT_SEARCHES, ADD_HOT_SEARCH, VOTE_HOT_SEARCH, BUY_HOT_SEARCH, EXIT };

        throw new IllegalArgumentException("user type not supported");
    }


    /**
     * Do not let the class to be instantiated
     */
    private PermissionManager() {}

}
