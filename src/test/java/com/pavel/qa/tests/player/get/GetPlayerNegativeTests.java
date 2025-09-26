package com.pavel.qa.tests.player.get;

import com.pavel.qa.base.BaseTest;
import com.pavel.qa.utils.PlayerApi;
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

    @Test(description = "Attempt to get player with invalid userId should return 404/404/403")
    @Description("Verify that request with invalid playerId returns error")
    @Story("Negative Test - Invalid playerId")
    @Severity(SeverityLevel.CRITICAL)
    public void getPlayerWithInvalidPlayerId_NegativeTest() {
        Allure.step("Step 1: Prepare invalid playerId");
        String invalidPlayerId = "nonexistent-id-12345"; // could also be a number like "999999999"

        Allure.step("Step 2: Send get player request with invalid playerId");
        Response response = PlayerApi.sendGetPlayerRequest(invalidPlayerId);
        Allure.addAttachment("Get Player Response (Invalid ID)", "application/json", response.asString());

        Allure.step("Step 3: Validate error response");
        Assert.assertNotEquals(response.statusCode(), 200, "Expected error response for invalid playerId");
        Assert.assertTrue(response.statusCode() == 400 || response.statusCode() == 404 || response.statusCode() == 403,
                "Expected 400/403/404 for invalid playerId, but got: " + response.statusCode());
    }
}
