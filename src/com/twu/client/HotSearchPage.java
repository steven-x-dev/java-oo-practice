package com.twu.client;

import com.twu.controller.HotSearchController;
import com.twu.entity.HotSearch;
import com.twu.entity.RegularUser;
import com.twu.entity.User;
import com.twu.service.Session;
import com.twu.utility.InteractionUtil;

import java.util.List;


/**
 *
 */
class HotSearchPage {


    /**
     * The context that calls this HotSearchPage, used to get session information
     */
    private App context;


    /**
     * The HotSearchController that handles user requests
     */
    private HotSearchController hotSearchController;


    /**
     * The key to set and get parameters from the session for the "buy hot search" request
     */
    private static final String BUY_HOT_SEARCH = "BUY_HOT_SEARCH";


    /**
     * The key to set and get parameters from the session for the "vote hot search" request
     */
    private static final String VOTE_HOT_SEARCH = "VOTE_HOT_SEARCH";


    HotSearchPage(App context) {
        this.context = context;
        hotSearchController = HotSearchController.getInstance();
    }


    /**
     * Prompt the user to buy a rank on the list for a hot search
     */
    void buyHotSearch() {

        Session session = context.getSession();

        if (session.getRequestParams(BUY_HOT_SEARCH) == null)
            session.addRequest(BUY_HOT_SEARCH, 3);

        HotSearch hs = findHotSearch("请输入您要购买的热搜名：");
        session.setParam(BUY_HOT_SEARCH, 0, hs);

        int rank = askRank();
        session.setParam(BUY_HOT_SEARCH, 1, rank);

        askAmountThenSubmitRequest();

        session.deleteRequest(BUY_HOT_SEARCH);

        System.out.println("热搜购买成功");
        System.out.println();
    }


    /**
     * Prompt the user to vote a hot search
     */
    void voteHotSearch() {

        Session session = context.getSession();

        User authenticatedUser = session.getUser();

        if (authenticatedUser == null || authenticatedUser.getClass() != RegularUser.class)
            return;

        if (session.getRequestParams(VOTE_HOT_SEARCH) == null)
            session.addRequest(VOTE_HOT_SEARCH, 2);

        Object[] params = session.getRequestParams(VOTE_HOT_SEARCH);

        HotSearch hs = findHotSearch("请输入您要投票的热搜名：");
        session.setParam(VOTE_HOT_SEARCH, 0, hs);

        int votes = getVotes((RegularUser) authenticatedUser);
        session.setParam(VOTE_HOT_SEARCH, 1, votes);

        hotSearchController.
                voteHotSearch(((HotSearch) params[0]).getName(), authenticatedUser.getUsername(), (int) params[1]);

        session.deleteRequest(VOTE_HOT_SEARCH);

        System.out.println("热搜投票成功");
        System.out.println();
    }


    /**
     * List all hot searches
     */
    void listAllHotSearches() {

        List<HotSearch> hotSearches = hotSearchController.findAll();

        if (hotSearches == null || hotSearches.size() == 0) {

            System.out.println("热搜列表为空");

        } else {

            for (int i = 0; i < hotSearches.size(); i++)
                System.out.println(String.format("%d. %s", i + 1, hotSearches.get(i).toString()));
        }

        System.out.println();
    }


    /**
     * Prompt the user to add a super hot search with a name from user input
     */
    void addSuperHotSearch() {

        System.out.println("请输入您要添加的超级热搜名：");
        System.out.println();

        String name = null;

        while (name == null)
            name = askHotSearchName();

        hotSearchController.addSuperHotSearch(name);

        System.out.println("超级热搜添加成功");
        System.out.println();
    }


    /**
     * Prompt the user to add a hot search with a name from user input
     */
    void addHotSearch() {

        System.out.println("请输入您要添加的热搜名：");
        System.out.println();

        String name = null;

        while (name == null)
            name = askHotSearchName();

        hotSearchController.addHotSearch(name);

        System.out.println("热搜添加成功");
        System.out.println();
    }


    /**
     * Get a viable rank that the user can buy for a hot search by the user input
     *
     * @return a viable rank validated by the server
     */
    private int askRank() {

        Session session = context.getSession();
        HotSearch hs = (HotSearch) session.getRequestParams(BUY_HOT_SEARCH)[0];

        System.out.println("请输入您要购买的排名：");
        System.out.println();

        while (true) {

            int rank = InteractionUtil.getPositiveInteger("您要购买的排名");

            if (rank > hotSearchController.getCount()) {
                System.out.println("您要购买的热搜排名超过热搜数量，请重新输入：");
                System.out.println();
                continue;
            }

            int oldIndex = hotSearchController.getIndexByName(hs.getName());
            if (hs.getAmount() > 0 && rank > oldIndex + 1) {
                System.out.println(String.format("您要购买的热搜排比当前排名靠后，当前排名%d，请重新输入：", oldIndex + 1));
                System.out.println();
                continue;
            }

            return rank;
        }
    }


    private void askAmountThenSubmitRequest() {

        System.out.println("请输入您要购买的金额：");
        System.out.println();

        Session session = context.getSession();
        Object[] params = session.getRequestParams(BUY_HOT_SEARCH);

        while (true) {

            int amount = InteractionUtil.getPositiveInteger("您要购买的金额");
            session.setParam(BUY_HOT_SEARCH, 2, amount);

            int res = hotSearchController.buyHotSearch(
                    ((HotSearch) params[0]).getName(),
                    (int) params[1],
                    (int) params[2]);

            if (res == 1)
                return;

            if (res == 0)
                System.out.println("您所购买的热搜排名已被占用，请提高出价：");
            else if (res == -1)
                System.out.println("内部服务器错误，请重新输入价格：");

            System.out.println();
        }
    }


    /**
     * Get a viable number of votes that the user can use to vote a hot search by the user input
     *
     * @param regularUser the regular user who will use the votes
     * @return a viable number of votes validated by the server
     */
    private int getVotes(RegularUser regularUser) {

        System.out.println("请输入您要投票的数量：");
        System.out.println();

        while (true) {

            int votes = InteractionUtil.getPositiveInteger("您要投票的数量");

            int remaining = regularUser.getVotes();

            if (votes > remaining) {
                System.out.println(String.format("您当前剩余%d票，请重新输入您要投票的数量：", remaining));
                System.out.println();
                continue;
            }

            return votes;
        }
    }


    /**
     * Ask the user for a hot search by prompting the user to enter a hot search name.
     * The process repeats until the user has entered a name that exists
     *
     * @param msg the message to prompt the user of what the hot search is for
     * @return the hot search matching the name entered by user input that exists
     */
    private HotSearch findHotSearch(String msg) {

        System.out.println(msg);
        System.out.println();

        while (true) {

            String name = InteractionUtil.getNonEmptyString("热搜名不能为空，请重新输入：");

            if (name == null)
                continue;

            HotSearch hs = hotSearchController.findOneByName(name);

            if (hs == null) {
                System.out.println("热搜名不存在，请重新输入：");
                System.out.println();
                continue;
            }

            return hs;
        }
    }


    /**
     * Prompt the user to enter a hot search name, and repeats
     * until the user has entered a name that does not exist
     *
     * @return a hot search name that does not exist
     */
    private String askHotSearchName() {

        String name = InteractionUtil.getNonEmptyString("热搜名不能为空，请重新输入：");

        if (name == null)
            return null;

        if (hotSearchController.exists(name)) {
            System.out.println("热搜名已存在，请重新输入：");
            System.out.println();
            return null;
        }

        return name;
    }

}
