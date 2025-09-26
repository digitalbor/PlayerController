package com.pavel.qa.steps;

import com.pavel.qa.utils.TestDataGenerator;
import io.qameta.allure.Step;

public class TestDataSteps {

    @Step("Generate unique login")
    public static String generateLogin() {
        return TestDataGenerator.generateUniqueLogin();
    }

    @Step("Generate valid password")
    public static String generatePassword() {
        return TestDataGenerator.generateValidPassword();
    }

    @Step("Generate random gender")
    public static String generateGender() {
        return TestDataGenerator.getRandomGender();
    }

    @Step("Generate valid age")
    public static String generateAge() {
        return TestDataGenerator.generateValidAge();
    }

    @Step("Generate unique screenName")
    public static String generateScreenName() {
        return TestDataGenerator.generateValidAge();
    }
}


