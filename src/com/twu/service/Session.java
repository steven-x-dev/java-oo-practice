package com.twu.service;

import com.twu.entity.User;


/**
 * A Session instance is created when a user logs in or registers
 * an account (which automatically logs in), and stores essential
 * information that the server keeps to remember the state of the
 * conversation with the client
 */
public class Session {


    /**
     * The logged-in user associated with this session
     */
    private User user;


    /**
     * When a Session instance is created, it only stores the
     * logged-in user. Other information may be added later on
     *
     * @param user the logged-in user associated with this session
     */
    Session(User user) {
        this.user = user;
    }


    public User getUser() {
        return user;
    }


    /**
     * When a session expires or the user logs out, the session
     * will be destroyed, and the information stored in the
     * session will be marked for GC
     */
    void destroy() {
        user = null;
    }

}
