package com.olshevchenko.movielandparser.service;

/**
 * @author Oleksandr Shevchenko
 */
public class Utils {

    static String removeWaste(String stringWithWaste) {
        if (stringWithWaste.contains("\uFEFF")) {
            stringWithWaste = stringWithWaste.split("\uFEFF")[1];
        }
        return stringWithWaste;
    }
}
