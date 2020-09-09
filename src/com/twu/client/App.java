package com.twu.client;

import com.twu.service.Session;
import com.twu.service.SessionManager;


/**
 * The "index" of the application responsible for keeping track of
 * the user's current location inside the app, directing the user
 * to the next location, and handling the response of each user
 * action using a loop so that the app can keep running
 */
public class App {


    /**
     * A client's copy of the session established with the server
     */
    private Session session;


    /**
     * A HomePage instance of the current authenticated user
     */
    private HomePage homePage;


    /**
     * A UserPage instance of the current authenticated user
     */
    private UserPage userPage;


    /**
     * A HotSearchPage instance of the current authenticated user
     */
    private HotSearchPage hotSearchPage;


    /**
     * Start the app
     */
    public void start() {

        int res = displayHomePage();

        while (res == 0) {

            if (session != null && session.getUser() != null)
                SessionManager.getInstance().destroySession(session);

            session = null;
            homePage = null;
            userPage = null;
            hotSearchPage = null;

            res = displayHomePage();
        }
    }

    void setSession(Session session) {
        this.session = session;
    }

    Session getSession() {
        return session;
    }


    /**
     * Display the home page, ask the user for his/her user type,
     * authenticate the user, and direct the user to the user
     * dashboard if the user has been authenticated
     *
     * @return 1 if the user has been directed to the user dashboard, or
     *         0 otherwise (then exit)
     */
    private int displayHomePage() {

        if (homePage == null)
            homePage = new HomePage();

        HomeOption homeOption = homePage.askUserType();

        int res = displayUserDashboard(homeOption);

        while (res == 1)
            res = displayUserDashboard(homeOption);

        return res;
    }


    /**
     * Ask the user for credentials and authenticate the user
     * based on the user type specified by homeOption.
     *
     * When the login completes, display the user dashboard
     * and ask the user for any activity that he/she may want.
     *
     * Then the interactive prompt-input module will be displayed
     * and ask the user for parameters that are necessary to
     * complete the activity that he/she chooses
     *
     * When the parameters are complete and the request completes,
     * the type of the activity chosen by the user will be returned.
     *
     * @param homeOption the HomeOption that redirected user from
     *                   the home page to the user dashboard,
     *                   either ADMIN_USER or REGULAR_USER
     *
     * @return 0 if the user wants to exit from the user dashboard, or
     *         1 otherwise
     */
    private int displayUserDashboard(HomeOption homeOption) {

        if (userPage == null)
            userPage = new UserPage(App.this, homeOption);

        UserOption activity = userPage.askActivity();

        return displayHotSearchPage(activity);
    }


    /**
     * Direct the user to a certain activity related to the hot searches based
     * on the user option that he/she has selected from the user dashboard
     *
     * @param userOption the option selected from the user dashboard
     * @return 1 if the user has selected any hot search-related activity, or
     *         0 otherwise (then logout)
     */
    private int displayHotSearchPage(UserOption userOption) {

        if (hotSearchPage == null)
            hotSearchPage = new HotSearchPage(App.this);

        switch (userOption) {

            case SEE_HOT_SEARCHES:
                hotSearchPage.listAllHotSearches();
                return 1;

            case ADD_HOT_SEARCH:
                hotSearchPage.addHotSearch();
                return 1;

            case ADD_SUPER_HOT_SEARCH:
                hotSearchPage.addSuperHotSearch();
                return 1;

            case VOTE_HOT_SEARCH:
                hotSearchPage.voteHotSearch();
                return 1;

            case BUY_HOT_SEARCH:
                hotSearchPage.buyHotSearch();
                return 1;

            case EXIT:
                return 0;

            default:
                return 0;
        }
    }

}
