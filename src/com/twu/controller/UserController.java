package com.twu.controller;

import com.twu.entity.AdminUser;
import com.twu.entity.RegularUser;
import com.twu.entity.User;
import com.twu.service.UserService;


/**
 * The UserController is a singleton that processes user requests by calling
 * corresponding services to access the data. Each method in the controller
 * is a transaction
 */
public class UserController {


    /**
     * The singleton instance
     */
    private static UserController instance = new UserController();


    /**
     * The UserService instance that the controller directs user requests to
     */
    private UserService userService = UserService.getInstance();


    /**
     * @see UserService#findOneAdminUserByUsername(String)
     */
    public AdminUser findOneAdminUserByUsername(String username) {
        return userService.findOneAdminUserByUsername(username);
    }


    /**
     * @see UserService#existsAdminUser(String)
     */
    public boolean existsAdminUser(String username) {
        return userService.existsAdminUser(username);
    }


    /**
     * @see UserService#existsAdminUser(String)
     */
    public RegularUser findOneRegularUserByUsername(String username) {
        return userService.findOneRegularUserByUsername(username);
    }


    /**
     * @see UserService#addRegularUser(String)
     */
    public User addRegularUser(String username) {
        return userService.addRegularUser(username);
    }


    /**
     * Do not let this class to be instantiated externally
     */
    private UserController() {}


    /**
     * Return the singleton instance of UserController
     */
    public static UserController getInstance() {
        return instance;
    }

}
