package com.twu.utility;

import java.util.stream.Collector;
import java.util.stream.Collectors;


/**
 * Utility methods for performing operations on stream
 */
public class StreamUtil {


    /**
     * Returns a collector that gets the first item in the stream
     * or gets null if the stream does not have any element
     *
     * @param <T> the type parameter of the elements in the stream
     * @return the collector that gets the first item in the stream,
     *         or gets null if the stream does not have any element
     */
    public static <T> Collector<T, ?, T> limitOne() {
        return Collectors.collectingAndThen(Collectors.toList(), list -> list.size() > 0 ? list.get(0) : null);
    }


    /**
     * Do not let this class to be instantiated
     */
    private StreamUtil() {}

}
