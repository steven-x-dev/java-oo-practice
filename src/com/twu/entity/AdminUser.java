package com.twu.entity;


/**
 * Admin user who must be authenticated by the password
 * in order to login to access its accessible features
 */
public class AdminUser extends User {


    private String password;


    public AdminUser(String username, String password) {
        super(username);
        this.password = password;
    }


    public String getPassword() {
        return password;
    }

}
