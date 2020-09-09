package com.twu.client;

import com.twu.controller.UserController;
import com.twu.entity.AdminUser;
import com.twu.entity.RegularUser;
import com.twu.entity.User;
import com.twu.service.Session;
import com.twu.service.SessionManager;
import com.twu.utility.*;

import java.util.Arrays;
import java.util.function.Predicate;


/**
 * The UserPage is displayed when the user has selected a certain
 * user type from HomePage.
 *
 * The UserPage is responsible for login, displaying the user's
 * dashboard with accessible features for the corresponding user
 * type, and asking the user for any activity that he/she may want.
 *
 * First, the UserPage will ask the user for his/her username.
 * In case of an admin user, the username has to match an existing
 * record in the list of admin users, and then the user has to enter
 * the correct password in order to log in. In case of a regular user,
 * if the username matches an existing record in the list of regular
 * users, user is logged in; otherwise a new user will be created and
 * the user is logged in automatically
 */
class UserPage {


    /**
     * The context that calls this UserPage, used to establish session
     */
    private App context;


    /**
     * The options to be displayed on the user's dashboard
     */
    private UserOption[] userOptions;


    /**
     * The UserController that handles user requests
     */
    private UserController userController;


    /**
     * Initialize the UserPage. First, authenticate the user
     * using the corresponding method of the user type specified
     * by homeOption, and establish session if login is successful.
     *
     * If login is successful, go through the entire list of options
     * and select those accessible to the user by checking each option
     * against the permission table
     */
    UserPage(App context, HomeOption homeOption) {

        this.context = context;

        userController = UserController.getInstance();

        Session session = null;

        switch (homeOption) {
            case ADMIN_USER:
                session = authenticateAdminUser();
                break;
            case REGULAR_USER:
                session = authenticateRegularUser();
                break;
        }

        if (session == null) return;

        this.context.setSession(session);

        userOptions = Arrays.stream(UserOption.values())
                .filter(canAccessFeature(PermissionManager.grantAccess(session.getUser().getClass())))
                .toArray(UserOption[]::new);
    }


    /**
     * Ask the user for any activity that he/she may want
     *
     * @return the option that the user has selected
     */
    UserOption askActivity() {

        User user = context.getSession().getUser();

        if (user == null)
            return null;

        String title = user.getUsername() + "你好，你可以：";
        String optionName = "您想要使用的功能";

        return InteractionUtil.getCorrectOption(userOptions, title, optionName);
    }


    /**
     * Authenticate the admin user and establish session if the
     * user is authenticated
     *
     * @return the client's copy of the established session if
     *         the user is authenticated
     */
    private Session authenticateAdminUser() {

        AdminUser adminUser = getAdminUser();

        System.out.println("请输入您的密码：");
        System.out.println();

        while (true) {

            String password = InteractionUtil.getNonEmptyString("输入不合法，请重新输入密码：");

            if (password == null)
                continue;

            if (!adminUser.getPassword().equals(password)) {
                System.out.println("密码错误，请重新输入：");
                System.out.println();
                continue;
            }

            return SessionManager.getInstance().createSession(adminUser);
        }
    }


    private AdminUser getAdminUser() {

        System.out.println("请输入您的昵称：");
        System.out.println();

        AdminUser adminUser = null;

        while (adminUser == null)
            adminUser = askAdminUser();

        return adminUser;
    }


    private AdminUser askAdminUser() {

        String username = InteractionUtil.getNonEmptyString("输入不合法，请重新输入昵称：");

        if (username == null)
            return null;

        AdminUser adminUser = userController.findOneAdminUserByUsername(username);

        if (adminUser == null) {
            System.out.println("管理员不存在，请重新输入昵称：");
            System.out.println();
            return null;
        }

        return adminUser;
    }


    /**
     * Authenticate the regular user and establish session if the
     * user is authenticated
     *
     * @return the client's copy of the established session if
     *         the user is authenticated
     */
    private Session authenticateRegularUser() {

        System.out.println("请输入您的昵称：");
        System.out.println();

        while (true) {

            String username = InteractionUtil.getNonEmptyString("输入不合法，请重新输入昵称：");

            if (username == null)
                continue;

            if (userController.existsAdminUser(username)) {
                System.out.println("昵称不可用，请重新输入昵称：");
                System.out.println();
                continue;
            }

            RegularUser regularUser = userController.findOneRegularUserByUsername(username);

            User authenticatedUser;

            if (regularUser == null) {

                User newUser = userController.addRegularUser(username);

                if (newUser == null) {
                    System.out.println("内部服务器错误，请重新输入昵称：");
                    System.out.println();
                    continue;
                } else {
                    authenticatedUser = newUser;
                }

            } else {
                authenticatedUser = regularUser;
            }

            return SessionManager.getInstance().createSession(authenticatedUser);
        }
    }


    /**
     * Returns a predicate that checks whether the user option is accessible
     * to the current user by comparing the option against the accessible features
     *
     * @param features the accessible features of the current user
     * @return the predicate that checks whether the option is accessible to the current user
     */
    private static Predicate<UserOption> canAccessFeature(Feature[] features) {

        return userOption -> {

            Feature f = userOption.getFeature();

            for (Feature feature : features)
                if (f == feature) return true;

            return false;
        };
    }

}
