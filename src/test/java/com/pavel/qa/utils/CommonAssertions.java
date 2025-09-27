package com.pavel.qa.utils;

import io.qameta.allure.Allure;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Map;

public class CommonAssertions {

    //Validate and Delete response JSON structures are the same
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

    public static void validateDeleteUserJsonStructure(Response response) {
        Allure.step("Step 4: Validate JSON structure of delete response");

        JsonPath json = response.jsonPath();
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(json.get("message"), "Response should contain 'message'");
        softAssert.assertTrue(json.get("message") instanceof String, "'message' should be a string");

        softAssert.assertAll();
    }

    public static void validateGetAllJsonStructure(Response response) {
        Allure.step("Step 4: Validate JSON structure of get all response");

        JsonPath json = response.jsonPath();
        SoftAssert softAssert = new SoftAssert();
        List<Map<String, Object>> players = json.getList("players");


        softAssert.assertNotNull(players, "'players' list should not be null");
        softAssert.assertTrue(!players.isEmpty(), "'players' list should not be empty");

        Map<String, Object> firstPlayer = players.get(0);
        softAssert.assertTrue(firstPlayer.containsKey("id"), "Missing 'id'");
        softAssert.assertTrue(firstPlayer.containsKey("screenName"), "Missing 'screenName'");
        softAssert.assertTrue(firstPlayer.containsKey("gender"), "Missing 'gender'");
        softAssert.assertTrue(firstPlayer.containsKey("age"), "Missing 'age'");
        softAssert.assertTrue(firstPlayer.containsKey("role"), "Missing 'role'");

        softAssert.assertAll();

    }
}
