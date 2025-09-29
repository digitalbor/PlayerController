
package com.pavel.qa.tests.player.delete;

import com.pavel.qa.base.BaseTest;
import com.pavel.qa.generators.TestDataGenerator;
import com.pavel.qa.models.*;
import com.pavel.qa.utils.*;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.Test;

@Epic("PlayerController")
@Feature("Player Deletion")
@Tag("positive")
public class DeletePlayerPositiveTests extends BaseTest {

    @Test(description = "Delete user successfully by admin login")
    @Description("Verify that an admin can delete a user using login as editor and playerId in body")
    @Story("Positive Test - Admin deletes user")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteUserByAdminLogin_PositiveTest() {
        SoftAssert softAssert = new SoftAssert();

        Allure.step("Step 1: Create user to be deleted");
        CreatePlayerRequestModel userModel = new CreatePlayerRequestModel();
        userModel.setLogin(TestDataGenerator.generateUniqueLogin());
        userModel.setScreenName(TestDataGenerator.generateUniqueScreenName());
        userModel.setPassword(TestDataGenerator.generateValidPassword());
        userModel.setGender(TestDataGenerator.getRandomGender());
        userModel.setAge(TestDataGenerator.generateValidAge());
        userModel.setRole("user");
        userModel.setEditor("supervisor");

        Response userCreateResponse = PlayerApi.sendCreatePlayerRequest(userModel);
        Allure.addAttachment("Create User To Be Deleted Full Response", "text/plain", userCreateResponse.statusCode() + "\n" + userCreateResponse.getHeaders().toString() + "\n" + userCreateResponse.asString());

        softAssert.assertEquals(userCreateResponse.statusCode(), 200, "User creation failed");

        CreatePlayerResponseModel userResponseModel = userCreateResponse.as(CreatePlayerResponseModel.class);
        Long playerId = userResponseModel.getId();


        Allure.step("Step 2: Create admin player to perform deletion");
        CreatePlayerRequestModel adminModel = new CreatePlayerRequestModel();
        adminModel.setLogin(TestDataGenerator.generateUniqueLogin());
        adminModel.setScreenName(TestDataGenerator.generateUniqueScreenName());
        adminModel.setPassword(TestDataGenerator.generateValidPassword());
        adminModel.setGender(TestDataGenerator.getRandomGender());
        adminModel.setAge(TestDataGenerator.generateValidAge());
        adminModel.setRole("admin");
        adminModel.setEditor("supervisor");

        Response adminCreateResponse = PlayerApi.sendCreatePlayerRequest(adminModel);
        Allure.addAttachment("Create Admin Full Response", "text/plain", adminCreateResponse.statusCode() + "\n" + adminCreateResponse.getHeaders().toString() + "\n" + adminCreateResponse.asString());

        softAssert.assertEquals(adminCreateResponse.statusCode(), 200, "Admin creation failed");
        String editorLogin = adminModel.getLogin();

        Allure.step("Step 3: Send delete request using admin login");
        DeletePlayerRequestModel deleteRequest = new DeletePlayerRequestModel();
        deleteRequest.setPlayerId(playerId.intValue());
        deleteRequest.setEditor(adminModel.getLogin());

        Response deleteResponse = PlayerApi.sendDeletePlayerRequest(deleteRequest);
        Allure.addAttachment("Delete Player Full Response", "text/plain", deleteResponse.statusCode() + "\n" + deleteResponse.getHeaders().toString() + "\n" + deleteResponse.asString());
        softAssert.assertTrue(deleteResponse.statusCode() == 200 || deleteResponse.statusCode() == 204, "Expected status code 200 OK or 204 NO_CONTENT for successful deletion");

        Allure.step("Step 4: Verify the user no longer exists");
        GetPlayerByIdRequestModel getRequest = new GetPlayerByIdRequestModel();
        getRequest.setId(playerId);
        Response getResponse = PlayerApi.sendGetPlayerByIdRequest(getRequest);
        Allure.addAttachment("Get Deleted Player Full Response", "text/plain", getResponse.statusCode() + "\n" + getResponse.getHeaders().toString() + "\n" + getResponse.asString());
        softAssert.assertEquals(getResponse.statusCode(), 404, "Expected 404 NOT_FOUND for deleted user");

        softAssert.assertAll();
    }
}
