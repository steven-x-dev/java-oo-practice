package com.twu.entity;


/**
 * A super hot search is a special type of hot search
 * that receives the doubled number of votes when a
 * user adds a certain number of votes to it.
 *
 * Can only be created by admin users
 */
public class SuperHotSearch extends HotSearch {


    public SuperHotSearch(String name) {
        super(name);
    }


    /**
     * Add doubled number of votes to this super hot search
     *
     * @param vote the number of votes to be added
     * @return the updated votes of this hot search
     * @see HotSearch#addVotes(int)
     */
    public int addVotes(int vote) {
        return super.addVotes(vote * 2);
    }

}
