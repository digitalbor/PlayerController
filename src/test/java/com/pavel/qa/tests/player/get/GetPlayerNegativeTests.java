package com.pavel.qa.tests.player.get;

import com.pavel.qa.base.BaseTest;
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
@Tag("negative")
public class GetPlayerNegativeTests extends BaseTest {

    @Test(description = "Verify that an admin cannot get another admin's data")
    @Description("Ensure that users with role 'admin' cannot retrieve data of other users with role 'admin'")
    @Story("Negative Test - Admin cannot get another admin")
    @Severity(SeverityLevel.CRITICAL)
    public void adminCannotGetAnotherAdmin_NegativeTest() {
        Allure.step("Step 1: Create a second admin user to be retrieved");
        String login = TestDataGenerator.generateUniqueLogin();
        String screenName = TestDataGenerator.generateUniqueScreenName();
        String password = TestDataGenerator.generateValidPassword();
        String gender = TestDataGenerator.getRandomGender();
        String age = TestDataGenerator.generateValidAge();
        String role = "admin";
        String editor = "supervisor"; // only supervisor can create admins

        Response createResponse = PlayerApi.sendCreatePlayerRequest(editor, age, gender, login, password, role, screenName);
        Allure.addAttachment("Create Admin Response", "text/plain", createResponse.asString());
        Assert.assertEquals(createResponse.statusCode(), 200, "Admin creation failed before get attempt");

        String playerId = createResponse.jsonPath().getString("id");

        Allure.step("Step 2: Attempt to get admin user data using another admin");
        Response getResponse = PlayerApi.sendGetPlayerRequest(playerId);
        Allure.addAttachment("Get Attempt Response", "application/json", getResponse.asString());

        Allure.step("Step 3: Validate that retrieval is forbidden");
        Assert.assertEquals(getResponse.statusCode(), 403, "Expected 403 Forbidden when admin tries to get another admin's data");
    }
}

