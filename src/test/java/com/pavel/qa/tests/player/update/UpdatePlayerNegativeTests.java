package com.pavel.qa.tests.player.update;

import com.pavel.qa.base.BaseTest;
import com.pavel.qa.generators.TestDataGenerator;
import com.pavel.qa.models.*;
import com.pavel.qa.utils.PlayerApi;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Epic("PlayerController")
@Feature("Player Update")
@Tag("negative")
public class UpdatePlayerNegativeTests extends BaseTest {

    @Test(description = "Attempt to update player's screenName with empty value")
    @Description("Verify that updating a player's screenName with an empty string returns an error")
    @Story("Negative Test - Update screenName with invalid data")
    @Severity(SeverityLevel.CRITICAL)
    public void updatePlayerScreenName_EmptyValue_NegativeTest() {
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

        Allure.step("Step 2: Prepare update request with empty screenName");
        UpdatePlayerRequestModel updateModel = new UpdatePlayerRequestModel();
        updateModel.setScreenName("");// Invalid empty screenName
        updateModel.setEditor(editor);
        updateModel.setPlayerId(playerId);

        Allure.step("Step 3: Send update request");
        Response updateResponse = PlayerApi.sendUpdatePlayerRequest(updateModel);
        Allure.addAttachment("Update Player Full Response", "text/plain",
                updateResponse.statusCode() + "\n" +
                        updateResponse.getHeaders().toString() + "\n" +
                        updateResponse.asString());

        softAssert.assertEquals(updateResponse.statusCode(), 400, "Expected 400 Bad Request for empty screenName");

        softAssert.assertAll();
    }
}

