package com.twu.service;

import com.twu.entity.User;

import java.util.HashMap;
import java.util.Map;


/**
 * A Session instance is created when a user logs in or registers
 * an account (which automatically logs in), and stores essential
 * information that the server keeps to remember the state of the
 * conversation with the client, such as the parameters of requests
 * that the user is making
 */
public class Session {


    /**
     * The authenticated user associated with this session
     */
    private User user;


    /**
     * The parameters of requests that the user is making.
     * The key is the request's name and the value is the
     * parameters of the request
     */
    private Map<String, Object[]> requests;


    /**
     * When a Session instance is created, it stores the authenticated user
     * and requests is set to an empty HashMap
     *
     * @param user the authenticated user associated with this session
     */
    Session(User user) {
        this.user = user;
        requests = new HashMap<>();
    }


    public User getUser() {
        return user;
    }


    /**
     * Get the parameters of a request identified by its name
     *
     * @param requestName the name of the request
     * @return the parameters of the request
     */
    public Object[] getRequestParams(String requestName) {
        return requests.get(requestName);
    }


    /**
     * Create a new empty request, or throw IllegalStateException
     * if a request with the request name already exists
     *
     * @param requestName the name of the request
     * @param paramsLength the number of parameters of this request
     */
    public void addRequest(String requestName, int paramsLength) {

        if (requests.containsKey(requestName))
            throw new IllegalStateException("request already exists");

        requests.put(requestName, new Object[paramsLength]);
    }


    /**
     * Set the value of a certain field to the request identified by its name,
     * or throw IllegalStateException if the request with the request name does
     * not exist
     *
     * @param requestName the name of the request
     * @param paramIndex the index of the field in the parameters
     * @param value the value to be set to the field
     */
    public void setParam(String requestName, int paramIndex, Object value) {

        if (!requests.containsKey(requestName))
            throw new IllegalStateException("request does not exist");

        Object[] params = requests.get(requestName);
        params[paramIndex] = value;

        requests.put(requestName, params);
    }


    /**
     * Deletes a request identified by its name
     *
     * @param requestName the name of the request
     */
    public Object[] deleteRequest(String requestName) {
        return requests.remove(requestName);
    }


    /**
     * When a session expires or the user logs out, the session
     * will be destroyed, and the information stored in the
     * session will be marked for GC
     */
    void destroy() {
        user = null;
        requests = null;
    }

}
