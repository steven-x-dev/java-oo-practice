package com.twu.entity;


/**
 * Regular user who has certain amount of votes and can
 * use the remaining votes to vote a certain hot search
 */
public class RegularUser extends User {


    /**
     * The number of remaining votes
     */
    private int votes;


    /**
     * The initial number of votes is set to 10
     */
    public RegularUser(String username) {
        super(username);
        votes = 10;
    }


    public int getVotes() {
        return votes;
    }


    /**
     * Subtract a certain amount of votes from the user's
     * remaining votes. This method does not validate the
     * votes to be used against the remaining votes, and
     * whoever uses this method must validate it beforehand
     *
     * @param votes the number of votes to use
     */
    public void useVotes(int votes) {
        this.votes -= votes;
    }

}
