package com.pavel.qa.utils;

import io.qameta.allure.Allure;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.asserts.SoftAssert;

public class CommonAssertions {

    //Validate and Delete response JSON structures are the same
    public static void validateCreateUserJsonStructure(Response response) {
        Allure.step("Step 5: Validate JSON response structure");

        JsonPath json = response.jsonPath();
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(json.get("age"), "Response should contain 'age'");
        softAssert.assertNotNull(json.get("gender"), "Response should contain 'gender'");
        softAssert.assertNotNull(json.get("id"), "Response should contain 'id'");
        softAssert.assertNotNull(json.get("login"), "Response should contain 'login'");
        softAssert.assertNotNull(json.get("password"), "Response should contain 'password'");
        softAssert.assertNotNull(json.get("role"), "Response should contain 'role'");
        softAssert.assertNotNull(json.get("screenName"), "Response should contain 'screenName'");

        softAssert.assertTrue(json.get("age") instanceof Integer, "age should be an integer");
        softAssert.assertTrue(json.get("gender") instanceof String, "gender should be a string");
        softAssert.assertTrue(json.get("id") instanceof Integer, "id should be an integer");
        softAssert.assertTrue(json.get("login") instanceof String, "login should be a string");
        softAssert.assertTrue(json.get("password") instanceof String, "password should be a string");
        softAssert.assertTrue(json.get("role") instanceof String, "role should be a string");
        softAssert.assertTrue(json.get("screenName") instanceof String, "screenName should be a string");

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

        softAssert.assertNotNull(json.getList("players"), "'players' list should not be null");

        if (json.getList("players").size() > 0) {
            Object firstPlayer = json.getList("players").get(0);
            JsonPath playerJson = JsonPath.from(firstPlayer.toString());

            softAssert.assertNotNull(playerJson.get("age"), "Player should have 'age'");
            softAssert.assertNotNull(playerJson.get("gender"), "Player should have 'gender'");
            softAssert.assertNotNull(playerJson.get("id"), "Player should have 'id'");
            softAssert.assertNotNull(playerJson.get("role"), "Player should have 'role'");
            softAssert.assertNotNull(playerJson.get("screenName"), "Player should have 'screenName'");

            softAssert.assertAll();
        }
    }
}
