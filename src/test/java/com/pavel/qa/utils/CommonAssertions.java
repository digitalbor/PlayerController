package com.pavel.qa.utils;

import io.qameta.allure.Allure;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Map;

public class CommonAssertions {

    public static void validateCreatePlayerResponse(CreatePlayerResponseModel model) {
        Allure.step("Step 5: Validate response model structure");

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(model.getId(), "ID should not be null");
        softAssert.assertNotNull(model.getLogin(), "Login should not be null");
        softAssert.assertNotNull(model.getPassword(), "Password should not be null");
        softAssert.assertNotNull(model.getRole(), "Role should not be null");
        softAssert.assertNotNull(model.getScreenName(), "ScreenName should not be null");
        softAssert.assertNotNull(model.getGender(), "Gender should not be null");
        softAssert.assertTrue(model.getAge() > 0, "Age should be positive");

        softAssert.assertAll();
    }

    public static void validateGetPlayerByIdResponse(GetPlayerByIdResponseModel model) {
        Allure.step("Step 5: Validate response model structure");

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(model.getId(), "ID should not be null");
        softAssert.assertNotNull(model.getLogin(), "Login should not be null");
        softAssert.assertNotNull(model.getPassword(), "Password should not be null");
        softAssert.assertNotNull(model.getRole(), "Role should not be null");
        softAssert.assertNotNull(model.getScreenName(), "ScreenName should not be null");
        softAssert.assertNotNull(model.getGender(), "Gender should not be null");
        softAssert.assertTrue(model.getAge() > 0, "Age should be positive");

        softAssert.assertAll();
    }

}
