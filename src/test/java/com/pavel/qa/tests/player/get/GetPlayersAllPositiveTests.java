
package com.pavel.qa.tests.player.get;

import com.pavel.qa.base.BaseTest;
import com.pavel.qa.utils.CommonAssertions;
import com.pavel.qa.utils.PlayerApi;
import io.qameta.allure.Allure;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Epic("PlayerController")
@Feature("Get All Players")
@Tag("positive")
public class GetPlayersAllPositiveTests extends BaseTest {

    @Test(description = "Get all players successfully")
    @Description("Verify that the system returns a list of all players with correct structure")
    @Story("Positive Test - Get All Players")
    @Severity(SeverityLevel.CRITICAL)
    public void getAllPlayers_PositiveTest() {
        Allure.step("Step 1: Send GET request to /player/get/all");
        Response getAllResponse = PlayerApi.sendGetPlayersAllRequest();

        Allure.addAttachment("Get All Players Full Response", "text/plain", getAllResponse.statusCode() + "\n" + getAllResponse.getHeaders().toString() + "\n" + getAllResponse.asString());

        Allure.step("Step 2: Validate response status");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(getAllResponse.statusCode(), 200, "Expected 200 OK");

        CommonAssertions.validateGetAllJsonStructure(getAllResponse);
    }
}

