package com.twu.entity;


/**
 * The base class for all types of users with an immutable username
 */
public class User {


    protected final String USERNAME;


    protected User(String username) {
        USERNAME = username;
    }


    public String getUsername() {
        return USERNAME;
    }

}
