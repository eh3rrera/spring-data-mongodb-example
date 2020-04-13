package com.example.demo.domain;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum DifficultyLevel {
    EASY, NORMAL, HARD;

    /**
     * Method to get all the status values of this Enum as a comma-separated string
     * @return All the status values as a comma-separated string
     */
    public static String listOfValues() {
        return Arrays.stream(DifficultyLevel.values())
                .map(Enum::toString)
                .collect(Collectors.joining(",")).toLowerCase();
    }
}
