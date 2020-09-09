package com.twu.service;

import com.twu.entity.User;
import com.twu.utility.StreamUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


/**
 * The SessionManager is a singleton that manages the lifecycle
 * of sessions
 */
public class SessionManager {


    /**
     * The singleton instance
     */
    private static SessionManager instance = new SessionManager();


    /**
     * The currently active session
     */
    private List<Session> activeSessions;


    /**
     * Check whether a session with the user has already been established
     *
     * @param user the user to be checked
     * @return whether a session with the user has already been established
     */
    public boolean exists(User user) {
        return activeSessions.stream().anyMatch(match(user));
    }


    /**
     * When a user logs in, the SessionManager will create
     * a new session for that user. If the user is already
     * logged in and attempts to log in again, the previous
     * session will be destroyed
     *
     * @param user the user who is logging in
     * @return the newly created session for the user
     */
    public Session createSession(User user) {

        destroySession(user);

        Session session = new Session(user);
        activeSessions.add(session);

        return session;
    }


    /**
     * When a user logs out, the SessionManager will destroy
     * the session associated with that user
     */
    public void destroySession(User user) {

        Session activeSession = getActiveSession(user);

        if (activeSession != null) {
            activeSessions.remove(activeSession);
            activeSession.destroy();
        }
    }


    /**
     * Returns a predicate that checks whether the session matches the
     * specified user by username, case insensitive
     *
     * @param user the specified user to be checked
     * @return the predicate that checks whether the session matches the specified user
     */
    private static Predicate<Session> match(User user) {
        return u -> u.getUser().getUsername().equalsIgnoreCase(user.getUsername());
    }


    /**
     * Do not let this class to be instantiated externally.
     */
    private SessionManager() {
        init();
    }


    /**
     * Create an empty list at startup to store active sessions
     */
    private void init() {
        activeSessions = new ArrayList<>();
    }


    /**
     * Return the singleton instance of SessionManager
     */
    public static SessionManager getInstance() {
        return instance;
    }

}
