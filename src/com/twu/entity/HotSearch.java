package com.twu.entity;


/**
 * A regular hot search entity with an immutable name,
 * the amount of money that bought this hot search at
 * its current position on the ranking list, and the
 * number of votes it receives
 */
public class HotSearch {


    /**
     * The immutable hot search name, used as the
     * unique identifier of an entity
     */
    private final String NAME;

    /**
     * The amount of money that bought this hot search
     * at its current position on the ranking list
     */
    protected int amount;

    /**
     * The number of votes received by this hot search
     * representing its popularity
     */
    protected int vote;


    public HotSearch(String name) {
        NAME = name;
        amount = 0;
        vote = 0;
    }


    public String getName() {
        return NAME;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getVote() {
        return vote;
    }


    /**
     * Add certain votes to this hot search
     *
     * @param vote the number of votes to be added
     * @return the updated votes of this hot search
     */
    public int addVotes(int vote) {
        this.vote += vote;
        return this.vote;
    }


    /**
     * Generates a String representation of the hot search to be shown to the client,
     * which includes the name of the hot search and the number of votes it receives
     *
     * @return the String representation of the hot search to be shown to the client
     */
    @Override
    public String toString() {
        return NAME + " " + vote;
    }

}
