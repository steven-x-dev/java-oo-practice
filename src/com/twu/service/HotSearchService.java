package com.twu.service;

import com.twu.entity.HotSearch;
import com.twu.entity.SuperHotSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


/**
 * The HotSearchService is a singleton that manages a list of hot searches.
 * It accesses the hot search data and manages the CRUD operations of the data
 */
public class HotSearchService {


    /**
     * The singleton instance
     */
    private static HotSearchService instance = new HotSearchService();


    /**
     * The list of hot searches
     */
    private List<HotSearch> hotSearches;


    /**
     * Check whether a hot search name already exists, case insensitive
     *
     * @param name the specified hot search name to be checked
     * @return whether the specified hot search name exists
     */
    public boolean exists(String name) {
        return hotSearches.stream().anyMatch(matchByName(name));
    }


    /**
     * Get the count of all hot searches
     *
     * @return the count of all hot searches
     */
    public int getCount() {
        return hotSearches.size();
    }


    /**
     * Find a single hot search by the specified name, case insensitive
     *
     * @param name the specified hot search name
     * @return the hot search with the specified name, or null if one
     *         doesn't exist
     */
    public HotSearch findOneByName(String name) {
        return hotSearches.stream().filter(matchByName(name)).collect(StreamUtil.limitOne());
    }


    /**
     * Find all hot searches
     *
     * @return a copy of the list of all hot searches
     */
    public List<HotSearch> findAll() {
        return new ArrayList<>(hotSearches);
    }


    /**
     * Get the position of a hot search, identified by its name,
     * in the hot search list
     *
     * @param name the specified hot search name
     * @return the index of the hot search in the hot search list,
     *         or -1 if one doesn't exist
     */
    public int getIndexByName(String name) {
        for (int i = 0; i < hotSearches.size(); i++) {
            if (hotSearches.get(i).getName().equalsIgnoreCase(name))
                return i;
        }
        return -1;
    }


    /**
     * Add a hot search to the hot search list.
     * This method does not check whether the hot search name already
     * exists, and whoever uses this method must check it beforehand
     *
     * @param name the name of the hot search to be added
     */
    public void addHotSearch(String name) {
        hotSearches.add(new HotSearch(name));
    }


    /**
     * Add a super hot search to the hot search list.
     * This method does not check whether the hot search name already
     * exists, and whoever uses this method must check it beforehand
     *
     * @param name the name of the super hot search to be added
     */
    public void addSuperHotSearch(String name) {
        hotSearches.add(new SuperHotSearch(name));
    }


    /**
     * Buy a given position on the hot search list for a hot search
     * with a certain amount.
     *
     * If the existing hot search at the given rank is a different hot
     * search from the one specified by the name, the current hot search
     * will replace the original one and the original one will disappear.
     * All hot searches after the original position of the hot search
     * being bought will step up by one in the list
     *
     * @param name the name of the hot search
     * @param rank the 1-based index on the hot search list to be bought
     * @param amount the amount charged in this purchase
     *
     * @return  1 if the operation is successful, or
     *          0 if the existing hot search at the given rank is a different
     *               hot search from the one specified by the name, and the
     *               amount of that hot search is equal to or greater than
     *               the current amount, or
     *         -1 if the hot search with the specified name doesn't exist
     */
    public int buyHotSearch(String name, int rank, int amount) {

        int index = rank - 1;

        HotSearch toBuy = findOneByName(name);

        if (toBuy == null)
            return -1;

        HotSearch existing = hotSearches.get(index);

        int newPrice = toBuy.getAmount() + amount;

        if (toBuy == existing) {
            toBuy.setAmount(newPrice);
            return 1;
        }

        if (existing.getAmount() >= newPrice)
            return 0;

        toBuy.setAmount(newPrice);

        int oldIndex = hotSearches.indexOf(toBuy);

        hotSearches.set(index, toBuy);
        hotSearches.remove(oldIndex);

        return 1;
    }


    /**
     * Add votes to the hot search identified by the name.
     *
     * If the voted hot search is already bought at that position,
     * no re-ordering will take place. Otherwise, the voted one
     * will advance its place by comparing its votes with other
     * hot searches that have not been bought. If the hot search
     * is moved to a new location, it is guaranteed that the one
     * after its new location has not been bought, therefore other
     * bought hot searches between [newIndex + 2, oldIndex] in
     * the reordered-list have stepped back by one, and their
     * positions must be restored
     *
     * @param name the name of the hot search
     * @param votes the number of votes from the user to be added
     * @return true  if the operation is successful, or
     *         false if the hot search with the specified name doesn't exist
     */
    public boolean voteHotSearch(String name, int votes) {

        HotSearch hs = null;
        int oldIndex = -1;

        for (int i = 0; i < hotSearches.size(); i++) {
            HotSearch curr = hotSearches.get(i);
            if (curr.getName().equalsIgnoreCase(name)) {
                hs = curr;
                oldIndex = i;
                break;
            }
        }

        if (hs == null)
            return false;

        int newVote = hs.addVotes(votes);

        if (hs.getAmount() > 0)
            return true;

        int newIndex = oldIndex;

        for (int i = 0; i < hotSearches.size(); i++) {

            HotSearch curr = hotSearches.get(i);

            if (curr.getAmount() == 0 && newVote > curr.getVote()) {
                newIndex = i;
                break;
            }
        }

        if (newIndex == oldIndex)
            return true;

        hotSearches.remove(hs);
        hotSearches.add(newIndex, hs);

        for (int i = newIndex + 2; i <= oldIndex; i++) {
            HotSearch curr = hotSearches.get(i);
            if (curr.getAmount() > 0) {
                HotSearch prev = hotSearches.set(i - 1, curr);
                hotSearches.set(i , prev);
            }
        }

        return true;
    }


    /**
     * Returns a predicate that checks whether the hot search matches the
     * specified hot search name by its name, case insensitive
     *
     * @param name the specified hot search name to be checked
     * @return the predicate that checks whether the hot search matches the name
     */
    private static Predicate<HotSearch> matchByName(String name) {
        return hs -> hs.getName().equalsIgnoreCase(name);
    }


    /**
     * Do not let this class to be instantiated externally
     */
    private HotSearchService() {
        init();
    }


    /**
     * Simulates the process of connecting to a database.
     * Create an empty list at startup to store hot searches
     */
    private void init() {
        hotSearches = new ArrayList<>();
    }


    /**
     * Return the singleton instance of HotSearchService
     */
    public static HotSearchService getInstance() {
        return instance;
    }

}
