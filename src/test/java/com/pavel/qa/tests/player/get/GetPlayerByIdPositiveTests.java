package com.pavel.qa.tests.player.get;

import com.pavel.qa.base.BaseTest;
import com.pavel.qa.utils.CommonAssertions;
import com.pavel.qa.utils.PlayerApi;
import com.pavel.qa.utils.TestDataGenerator;
import io.qameta.allure.Allure;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("PlayerController")
@Feature("Get Player")
@Tag("positive")
public class GetPlayerByIdPositiveTests extends BaseTest {

    @Test(description = "Get player by valid playerId")
    @Description("Verify that a valid player can be retrieved by playerId")
    @Story("Positive Test - Get Player by ID")
    @Severity(SeverityLevel.CRITICAL)
    public void getPlayerByPlayerId_PositiveTest() {
        Allure.step("Step 1: Create user to be retrieved");
        String login = TestDataGenerator.generateUniqueLogin();
        String screenName = TestDataGenerator.generateUniqueScreenName();
        String password = TestDataGenerator.generateValidPassword();
        String gender = TestDataGenerator.getRandomGender();
        String age = TestDataGenerator.generateValidAge();
        String role = "user";
        String editor = "supervisor";

        Response createResponse = PlayerApi.sendCreatePlayerRequest(editor, age, gender, login, password, role, screenName);
        Allure.addAttachment("Create Player Response", "text/plain", createResponse.asString());
        Assert.assertEquals(createResponse.statusCode(), 200, "User creation failed before retrieval");

        String playerId = createResponse.jsonPath().getString("id");

        Allure.step("Step 2: Send get player request");
        Response getResponse = PlayerApi.sendGetPlayerRequest(playerId);
        Allure.addAttachment("Get Player Response", "application/json", getResponse.asString());

        Allure.step("Step 3: Validate get response");
        Assert.assertEquals(getResponse.statusCode(), 200, "Expected 200 OK for get player request");

        CommonAssertions.validateCreateUserJsonStructure(getResponse);
    }
}

