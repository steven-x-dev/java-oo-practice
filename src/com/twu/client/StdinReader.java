package com.twu.client;

import java.util.Scanner;


/**
 * A singleton service utility that is responsible for reading inputs from stdin
 */
class StdinReader {


    /**
     * The singleton instance
     */
    private static StdinReader instance = new StdinReader();


    /**
     * The scanner that reads input from stdin
     */
    private Scanner sc;


    /**
     * Reads the line from stdin that the user has just input,
     * or throw IllegalStateException if the service is currently
     * disconnected
     *
     * @return the line that the user has just input into stdin
     */
    String readLine() {
        if (sc == null)
            throw new IllegalStateException("the service has been disconnected by the client");
        return sc.nextLine();
    }


    /**
     * If the service is currently disconnected, connect the service
     * by initializing the service again
     */
    void connect() {
        if (sc == null) init();
    }


    /**
     * Disconnects the service by setting the Scanner to be null
     */
    void disconnect() {
        sc = null;
    }


    /**
     * Do not let this class to be instantiated externally
     */
    private StdinReader() {
        init();
    }


    /**
     * At initialization, a new Scanner instance is created
     * from System.in
     */
    private void init() {
        sc = new Scanner(System.in);
    }


    /**
     * Return the singleton instance of StdinReader
     */
    static StdinReader getInstance() {
        return instance;
    }

}
