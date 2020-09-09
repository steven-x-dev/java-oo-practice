package com.twu.controller;

import com.twu.entity.HotSearch;
import com.twu.entity.RegularUser;
import com.twu.entity.User;
import com.twu.service.HotSearchService;
import com.twu.service.SessionManager;
import com.twu.service.UserService;

import java.util.List;


/**
 * The HotSearchController is a singleton that processes user requests by calling
 * corresponding services to access the data. Each method in the controller is a
 * transaction
 */
public class HotSearchController {


    /**
     * The singleton instance
     */
    private static HotSearchController instance = new HotSearchController();


    /**
     * The HotSearchService instance used to access hot search data
     */
    private HotSearchService hotSearchService = HotSearchService.getInstance();


    /**
     * The UserService instance used to access user data
     */
    private UserService userService = UserService.getInstance();


    /**
     * The SessionManager instance used to retrieve the requesting user's session
     */
    private SessionManager sessionManager = SessionManager.getInstance();


    /**
     * @see HotSearchService#exists(String)
     */
    public boolean exists(String name) {
        return hotSearchService.exists(name);
    }


    /**
     * @see HotSearchService#getCount()
     */
    public int getCount() {
        return hotSearchService.getCount();
    }


    /**
     * @see HotSearchService#findOneByName(String)
     */
    public HotSearch findOneByName(String name) {
        return hotSearchService.findOneByName(name);
    }


    /**
     * @see HotSearchService#findAll()
     */
    public List<HotSearch> findAll() {
        return hotSearchService.findAll();
    }


    /**
     * @see HotSearchService#getIndexByName(String)
     */
    public int getIndexByName(String name) {
        return hotSearchService.getIndexByName(name);
    }


    /**
     * Add a hot search to the hot search list.
     * Perform a check of the existence of the hot search name to
     * be added in existing hot searches, and add a new hot search
     * with this name if it doesn't exist
     *
     * @param name the name of the hot search to be added
     *
     * @return true  if the hot search name does not exist and the
     *               hot search has been successfully added, or
     *         false if the hot search name exists and the request
     *               has been refused
     *
     * @see HotSearchService#addHotSearch(String)
     */
    public boolean addHotSearch(String name) {

        HotSearch existing = hotSearchService.findOneByName(name);

        if (existing != null)
            return false;

        hotSearchService.addHotSearch(name);

        return true;
    }


    /**
     * @see HotSearchService#addSuperHotSearch(String)
     * @see HotSearchController#addHotSearch(String)
     */
    public boolean addSuperHotSearch(String name) {

        HotSearch existing = hotSearchService.findOneByName(name);

        if (existing != null)
            return false;

        hotSearchService.addSuperHotSearch(name);

        return true;
    }


    /**
     * @see HotSearchService#buyHotSearch(String, int, int)
     */
    public int buyHotSearch(String name, int rank, int amount) {
        return hotSearchService.buyHotSearch(name, rank, amount);
    }


    /**
     * Subtract a certain amount of votes from the remaining votes of the
     * regular user identified by the username, and votes to the hot search
     * identified by the name.
     *
     * @param name the name of the hot search
     * @param username the username of the requesting user
     * @param votes the number of votes from the user to be added to the
     *              hot search
     *
     * @return true  if the transaction is successful, or
     *         false if the user with the username doesn't exist, or the
     *               user exists but hasn't logged in, or other cases
     *               returning false from UserService#useVotes(String, int)
     *               or HotSearchService#voteHotSearch(String, int)
     *
     * @see HotSearchService#voteHotSearch(String, int)
     * @see UserService#findOneRegularUserByUsername(String)
     * @see UserService#useVotes(String, int)
     * @see SessionManager#exists(User)
     */
    public boolean voteHotSearch(String name, String username, int votes) {

        RegularUser authenticatedUser = userService.findOneRegularUserByUsername(username);

        if (authenticatedUser == null ||
                sessionManager.exists(authenticatedUser)) {
            return false;
        }

        return userService.useVotes(username, votes) &&
                hotSearchService.voteHotSearch(name, votes);
    }


    /**
     * Do not let this class to be instantiated externally
     */
    private HotSearchController() {}


    /**
     * Return the singleton instance of HotSearchController
     */
    public static HotSearchController getInstance() {
        return instance;
    }

}
