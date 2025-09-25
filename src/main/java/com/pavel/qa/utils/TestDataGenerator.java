package com.pavel.qa.utils;

import java.util.concurrent.ThreadLocalRandom;

public class TestDataGenerator {

    /**
     * Generates valid age from 17 to 59.
     */
    public static String generateValidAge() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(17, 60));
    }

    /**
     * Generates unique login based on current timestamp.
     */
    public static String generateUniqueLogin() {
        return "user_" + System.currentTimeMillis();
    }

    /**
     * Generates unique screenName name.
     */
    public static String generateUniqueScreenName() {
        return "Ok ScreenName" + ThreadLocalRandom.current().nextInt(1000, 9999);
    }

    /**
     * Generates valid password.
     */
    public static String generateValidPassword() {
        return "OkPass" + ThreadLocalRandom.current().nextInt(1, 999999999);
    }

    /**
     * Returns random gender value.
     */
    public static String getRandomGender() {
        return ThreadLocalRandom.current().nextBoolean() ? "male" : "female";
    }

    /**
     * Returns random role.
     */
    public static String getRandomRole() {
        return ThreadLocalRandom.current().nextBoolean() ? "user" : "admin";
    }
}

