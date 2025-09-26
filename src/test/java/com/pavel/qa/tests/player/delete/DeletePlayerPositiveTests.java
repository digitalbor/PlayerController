package com.pavel.qa.tests.player.delete;

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
@Feature("Player Deletion")
@Tag("positive")
public class DeletePlayerPositiveTests extends BaseTest {

    @Test(description = "Delete user successfully by supervisor")
    @Description("Verify that a supervisor can delete a user with valid playerId")
    @Story("Positive Test - Supervisor deletes user")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteUserBySupervisor_PositiveTest() {
        Allure.step("Step 1: Create user to be deleted");
        String login = TestDataGenerator.generateUniqueLogin();
        String screenName = TestDataGenerator.generateUniqueScreenName();
        String password = TestDataGenerator.generateValidPassword();
        String gender = TestDataGenerator.getRandomGender();
        String age = TestDataGenerator.generateValidAge();
        String role = "user";
        String editor = "supervisor";

        Response createResponse = PlayerApi.sendCreatePlayerRequest(editor, age, gender, login, password, role, screenName);
        Allure.addAttachment("Create Player Response", "text/plain", createResponse.asString());
        Assert.assertEquals(createResponse.statusCode(), 200, "User creation failed before deletion");

        String playerId = createResponse.jsonPath().getString("id");

        Allure.step("Step 2: Send delete request");
        Response deleteResponse = PlayerApi.sendDeletePlayerRequest(editor, playerId);
        Allure.addAttachment("Delete Player Response", "text/plain", deleteResponse.asString());

        Allure.step("Step 3: Validate delete response");
        Assert.assertEquals(deleteResponse.statusCode(), 204, "Expected 204 OK for successful deletion with no response body");

        Allure.step("Step 4: Verify the user does not exist anymore");
        Response getResponse = PlayerApi.sendGetPlayerRequest(playerId);
        Allure.addAttachment("Get Player Response", "text/plain", getResponse.asString());
        Assert.assertEquals(deleteResponse.statusCode(), 404, "Expected 404 OK for deleted user");
    }

    @Test(description = "Delete user successfully by admin")
    @Description("Verify that an admin can delete a user with valid playerId")
    @Story("Positive Test - Admin deletes user")
    @Severity(SeverityLevel.NORMAL)
    public void deleteUserByAdmin_PositiveTest() {
        Allure.step("Step 1: Create user to be deleted");
        String login = TestDataGenerator.generateUniqueLogin();
        String screenName = TestDataGenerator.generateUniqueScreenName();
        String password = TestDataGenerator.generateValidPassword();
        String gender = TestDataGenerator.getRandomGender();
        String age = TestDataGenerator.generateValidAge();
        String role = "user";
        String editor = "admin";

        Response createResponse = PlayerApi.sendCreatePlayerRequest(editor, age, gender, login, password, role, screenName);
        Allure.addAttachment("Create Player Response", "text/plain", createResponse.asString());
        Assert.assertEquals(createResponse.statusCode(), 200, "User creation failed before deletion with no response body");

        String playerId = createResponse.jsonPath().getString("id");

        Allure.step("Step 2: Send delete request");
        Response deleteResponse = PlayerApi.sendDeletePlayerRequest(editor, playerId);
        Allure.addAttachment("Delete Player Response", "text/plain", deleteResponse.asString());

        Allure.step("Step 3: Validate delete response");
        Assert.assertEquals(deleteResponse.statusCode(), 204, "Expected 204 OK for successful deletion");

        Allure.step("Step 4: Verify the user does not exist anymore");
        Response getResponse = PlayerApi.sendGetPlayerRequest(playerId);
        Allure.addAttachment("Get Player Response", "text/plain", getResponse.asString());
        Assert.assertEquals(deleteResponse.statusCode(), 404, "Expected 404 OK for deleted user");
    }
}

