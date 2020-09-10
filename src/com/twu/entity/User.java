package com.twu.entity;


/**
 * The base class for all types of users with an immutable username
 */
public abstract class User {


    /**
     * The immutable username, used as the
     * unique identifier of an entity
     */
    protected final String USERNAME;


    protected User(String username) {
        USERNAME = username;
    }


    public String getUsername() {
        return USERNAME;
    }

}
