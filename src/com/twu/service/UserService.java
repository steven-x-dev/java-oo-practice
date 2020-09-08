package com.twu.service;

import com.twu.entity.AdminUser;
import com.twu.entity.RegularUser;
import com.twu.entity.User;
import com.twu.utility.StreamUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


/**
 * The UserService is a singleton that manages a list of admin users
 * and a list of regular users that are registered. It accesses the
 * users' data and manages the CRUD operations of the data
 */
public class UserService {


    /**
     * The singleton instance
     */
    private static UserService instance = new UserService();


    /**
     * The list of admin users
     */
    private List<AdminUser> adminUsers;

    /**
     * The list of regular users
     */
    private List<RegularUser> regularUsers;


    /**
     * Check whether a username is already used by one of the admin users,
     * case insensitive
     *
     * @param username the specified username to be checked
     * @return whether the specified username is used by an admin user
     */
    public boolean existsAdminUser(String username) {
        return adminUsers.stream().anyMatch(matchByUsername(username));
    }


    /**
     * Find a single admin user by the specified username, case insensitive
     *
     * @param username the specified username
     * @return the admin user with the specified username, or null if one
     *         doesn't exist
     */
    public AdminUser findOneAdminUserByUsername(String username) {
        return adminUsers.stream().filter(matchByUsername(username)).collect(StreamUtil.limitOne());
    }


    /**
     * Find a single regular user by the specified username, case insensitive
     *
     * @param username the specified username
     * @return the regular user with the specified username, or null if one
     *         doesn't exist
     */
    public RegularUser findOneRegularUserByUsername(String username) {
        return regularUsers.stream().filter(matchByUsername(username)).collect(StreamUtil.limitOne());
    }


    /**
     * Register a new regular user with the specified username
     *
     * @param username the new regular user's username
     * @return the newly created regular user, or null if one
     *         with the specified username already exists
     */
    public RegularUser addRegularUser(String username) {

        User existing = getExisting(username);

        if (existing == null) {

            RegularUser newUser = new RegularUser(username);
            regularUsers.add(newUser);

            return newUser;

        } else {
            return null;
        }
    }


    /**
     * Subtract a certain amount of votes from the remaining
     * votes of the regular user identified by the username
     *
     * @param username the specified username to identify a
     *                 regular user
     *
     * @param votes    the votes to be subtracted from the
     *                 regular user's account
     *
     * @return true if the operation is successful, false if
     *         the username does not correspond to an existing
     *         regular user, or the user has insufficient
     *         balance on his/her votes
     */
    public boolean useVotes(String username, int votes) {

        RegularUser existing = findOneRegularUserByUsername(username);

        if (existing == null)
            return false;

        if (existing.getVotes() < votes)
            return false;

        existing.useVotes(votes);

        return true;
    }


    /**
     * Check whether a username is already used by one of the admin users
     * or the regular users, case insensitive
     *
     * @param username the specified username to be checked
     * @return whether the specified username is used by a user of either type
     */
    private User getExisting(String username) {

        User user = adminUsers.stream().filter(matchByUsername(username)).collect(StreamUtil.limitOne());

        if (user == null)
            user = regularUsers.stream().filter(matchByUsername(username)).collect(StreamUtil.limitOne());

        return user;
    }


    /**
     * Returns a predicate that checks whether the user matches the
     * specified username by his/her username, case insensitive
     *
     * @param username the specified username to be checked
     * @return the predicate that checks whether the user matches the username
     */
    private static Predicate<User> matchByUsername(String username) {
        return user -> user.getUsername().equalsIgnoreCase(username);
    }


    /**
     * Do not let this class to be instantiated externally
     */
    private UserService() {
        init();
    }


    /**
     * Simulates the process of connecting to a database.
     * In this demo, admin users are created automatically
     * at startup, and regular users are an empty list
     */
    private void init() {
        adminUsers = new ArrayList<AdminUser>() {{ add(new AdminUser("admin", "admin")); }};
        regularUsers = new ArrayList<>();
    }


    /**
     * Return the singleton instance of UserService
     */
    public static UserService getInstance() {
        return instance;
    }

}
