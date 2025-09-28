
package com.pavel.qa.tests.player.get;

import com.pavel.qa.base.BaseTest;
import com.pavel.qa.utils.*;
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
        Allure.step("Step 1: Create user to be retrieved (precondition)");
        CreatePlayerRequestModel model = new CreatePlayerRequestModel();
        model.setLogin(TestDataGenerator.generateUniqueLogin());
        model.setScreenName(TestDataGenerator.generateUniqueScreenName());
        model.setPassword(TestDataGenerator.generateValidPassword());
        model.setGender(TestDataGenerator.getRandomGender());
        model.setAge(TestDataGenerator.generateValidAge());
        model.setRole("user");
        model.setEditor("supervisor");

        Response createResponse = PlayerApi.sendCreatePlayerRequest(model);
        Allure.addAttachment("Create Player Full Response", "text/plain", createResponse.statusCode() + "\n" + createResponse.getHeaders().toString() + "\n" + createResponse.asString());
        Assert.assertEquals(createResponse.statusCode(), 200, "User creation failed before retrieval");

        Allure.step("Step 2: Retrieve player id");
        Long playerId = createResponse.jsonPath().getLong("id");

        Allure.step("Step 3: Send get player request");
        GetPlayerByIdRequestModel getRequest = new GetPlayerByIdRequestModel();
        getRequest.setId(playerId);
        Response getResponse = PlayerApi.sendGetPlayerByIdRequest(getRequest);
        Allure.addAttachment("Get Player Full Response", "text/plain", getResponse.statusCode() + "\n" + getResponse.getHeaders().toString() + "\n" + getResponse.asString());


        Allure.step("Step 4: Validate get response");
        Assert.assertEquals(getResponse.statusCode(), 200, "Expected 200 OK for get player request");

        GetPlayerByIdResponseModel responseModel = getResponse.as(GetPlayerByIdResponseModel.class);
        Assert.assertEquals(responseModel.getLogin(), model.getLogin(), "Login should match");
        Assert.assertEquals(responseModel.getRole(), model.getRole(), "Role should match");
        Assert.assertEquals(responseModel.getScreenName(), model.getScreenName(), "ScreenName should match");
        Assert.assertEquals(responseModel.getGender(), model.getGender(), "Gender should match");
        Assert.assertEquals(responseModel.getAge(), Integer.parseInt(model.getAge()), "Age should match");
        CommonAssertions.validateGetPlayerByIdResponse(responseModel);
    }
}
