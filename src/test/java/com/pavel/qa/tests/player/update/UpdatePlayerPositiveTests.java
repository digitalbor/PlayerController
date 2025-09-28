
package com.pavel.qa.tests.player.update;

import com.pavel.qa.base.BaseTest;
import com.pavel.qa.generators.TestDataGenerator;
import com.pavel.qa.models.*;
import com.pavel.qa.utils.*;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.pavel.qa.utils.DeserializationUtils;

@Epic("PlayerController")
@Feature("Player Update")
@Tag("positive")
public class UpdatePlayerPositiveTests extends BaseTest {

    @Test(description = "Update player's screenName successfully")
    @Description("Verify that a supervisor can update a user's screenName")
    @Story("Positive Test - Update screenName")
    @Severity(SeverityLevel.CRITICAL)
    public void updatePlayerScreenName_PositiveTest() {
        Allure.step("Step 1: Create user to be updated");
        String editor = "supervisor";
        CreatePlayerRequestModel createModel = new CreatePlayerRequestModel();
        createModel.setLogin(TestDataGenerator.generateUniqueLogin());
        createModel.setScreenName(TestDataGenerator.generateUniqueScreenName());
        createModel.setPassword(TestDataGenerator.generateValidPassword());
        createModel.setGender(TestDataGenerator.getRandomGender());
        createModel.setAge(TestDataGenerator.generateValidAge());
        createModel.setRole("user");
        createModel.setEditor(editor);

        Response createResponse = PlayerApi.sendCreatePlayerRequest(createModel);
        Allure.addAttachment("Create Player Full Response", "text/plain",
                createResponse.statusCode() + "\n" +
                        createResponse.getHeaders().toString() + "\n" +
                        createResponse.asString());

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(createResponse.statusCode(), 200, "User creation failed before update");

        CreatePlayerResponseModel createResponseModel = createResponse.as(CreatePlayerResponseModel.class);
        Long playerId = createResponseModel.getId();

        Allure.step("Step 2: Prepare update request");
        String updatedScreenName = TestDataGenerator.generateUniqueScreenName();
        UpdatePlayerRequestModel updateModel = new UpdatePlayerRequestModel();
        updateModel.setScreenName(updatedScreenName);

        Allure.step("Step 3: Send update request");
        Response updateResponse = PlayerApi.sendUpdatePlayerRequest(editor, playerId, updateModel);
        Allure.addAttachment("Update Player Full Response", "text/plain",
                updateResponse.statusCode() + "\n" +
                        updateResponse.getHeaders().toString() + "\n" +
                        updateResponse.asString());
        softAssert.assertEquals(updateResponse.statusCode(), 200, "Expected 200 OK for successful update");

        Allure.step("Step 4: Validate updated response");
        UpdatePlayerResponseModel responseModel = DeserializationUtils.safeDeserialize(updateResponse, UpdatePlayerResponseModel.class);
        softAssert.assertEquals(responseModel.getId(), playerId, "Player ID should match");
        softAssert.assertEquals(responseModel.getScreenName(), updatedScreenName, "ScreenName should be updated");

        Allure.step("Step 5: Send GET request to verify update");
        GetPlayerByIdRequestModel getRequest = new GetPlayerByIdRequestModel();
        getRequest.setId(playerId);
        Response getResponse = PlayerApi.sendGetPlayerByIdRequest(getRequest);
        Allure.addAttachment("Get Player Full Response", "text/plain", getResponse.statusCode() + "\n" + getResponse.getHeaders().toString() + "\n" + getResponse.asString());

        softAssert.assertEquals(getResponse.statusCode(), 200, "Expected 200 OK for GET request");
        GetPlayerByIdResponseModel getResponseModel = getResponse.as(GetPlayerByIdResponseModel.class);
        softAssert.assertEquals(getResponseModel.getScreenName(), updatedScreenName, "ScreenName should match after GET");

        softAssert.assertAll();
    }
}

