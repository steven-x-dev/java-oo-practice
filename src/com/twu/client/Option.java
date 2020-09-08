package com.twu.client;


/**
 * An interface for the presentation of options offered to
 * the user in different pages across the entire application
 */
public interface Option {


    /**
     * An option must be able to be displayed as a String
     *
     * @return the name of the option
     */
    String getName();

}
