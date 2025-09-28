package com.pavel.qa.tests.player.get;

import com.pavel.qa.base.BaseTest;
import com.pavel.qa.utils.PlayerApi;
import com.pavel.qa.utils.GetPlayerByIdRequestModel;
import io.qameta.allure.Allure;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Epic("PlayerController")
@Feature("Get Player")
@Tag("negative")
public class GetPlayerByIdNegativeTests extends BaseTest {

    @DataProvider(name = "invalidPlayerId")
    public Object[][] invalidPlayerId() {
        return new Object[][] {
                {-1L},                    // negative value
                {0L},                     // zero
                {999999999999L}          // not existing ID
        };
    }

    @Test(description = "Attempt to get player with invalid userId should return 404/400/403", dataProvider = "invalidPlayerId")
    @Description("Verify that request with invalid playerId returns error")
    @Story("Negative Test - Invalid playerId")
    @Severity(SeverityLevel.CRITICAL)
    public void getPlayerWithInvalidPlayerId_NegativeTest(Long invalidPlayerId) {

        Allure.step("Step 1: Send get player request with invalid playerId: " + invalidPlayerId);
        GetPlayerByIdRequestModel requestModel = new GetPlayerByIdRequestModel();
        requestModel.setId(invalidPlayerId);

        Response response = PlayerApi.sendGetPlayerByIdRequest(requestModel);
        Allure.addAttachment("Get Player Full Response", "text/plain", response.statusCode() + "\n" + response.getHeaders().toString() + "\n" + response.asString());

        Allure.step("Step 2: Validate error response");
        Assert.assertNotEquals(response.statusCode(), 200, "Expected error response for invalid playerId");
        Assert.assertTrue(response.statusCode() == 400 || response.statusCode() == 404 || response.statusCode() == 403,
                "Expected 400/403/404 for invalid playerId, but got: " + response.statusCode());
    }
}
