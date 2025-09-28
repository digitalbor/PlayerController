package com.pavel.qa.tests.player.delete;

import com.pavel.qa.base.BaseTest;
import com.pavel.qa.utils.*;
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
        String editor = "supervisor";
        CreatePlayerRequestModel model = new CreatePlayerRequestModel();
        model.setLogin(TestDataGenerator.generateUniqueLogin());
        model.setScreenName(TestDataGenerator.generateUniqueScreenName());
        model.setPassword(TestDataGenerator.generateValidPassword());
        model.setGender(TestDataGenerator.getRandomGender());
        model.setAge(TestDataGenerator.generateValidAge());
        model.setRole("user");
        model.setEditor(editor);

        Response createResponse = PlayerApi.sendCreatePlayerRequest(model);
        Allure.addAttachment("Create Player Full Response", "text/plain",
                createResponse.statusCode() + "\n" +
                        createResponse.getHeaders().toString() + "\n" +
                        createResponse.asString());

        Assert.assertEquals(createResponse.statusCode(), 200, "User creation failed before deletion");
        Long playerId = createResponse.jsonPath().getLong("id");

        Allure.step("Step 2: Send delete request");
        DeletePlayerRequestModel deleteRequest = new DeletePlayerRequestModel();
        deleteRequest.setEditor(editor);
        deleteRequest.setPlayerId(playerId.intValue());
        Response deleteResponse = PlayerApi.sendDeletePlayerRequest(deleteRequest);
        Allure.addAttachment("Delete Player Full Response", "text/plain",
                deleteResponse.statusCode() + "\n" +
                        deleteResponse.getHeaders().toString() + "\n" +
                        deleteResponse.asString());

        Allure.step("Step 3: Validate delete response");
        Assert.assertEquals(deleteResponse.statusCode(), 204, "Expected 204 NO_CONTENT for successful deletion");

        Allure.step("Step 4: Verify the user does not exist anymore");
        GetPlayerByIdRequestModel getRequestModel = new GetPlayerByIdRequestModel();
        getRequestModel.setId(playerId);
        Response getResponse = PlayerApi.sendGetPlayerByIdRequest(getRequestModel);
        Allure.addAttachment("Get Player Full Response", "text/plain",
                getResponse.statusCode() + "\n" +
                        getResponse.getHeaders().toString() + "\n" +
                        getResponse.asString());

        Assert.assertEquals(getResponse.statusCode(), 404, "Expected 404 NOT_FOUND for deleted user");
    }

    @Test(description = "Delete user successfully by admin")
    @Description("Verify that an admin can delete a user with valid playerId")
    @Story("Positive Test - Admin deletes user")
    @Severity(SeverityLevel.NORMAL)
    public void deleteUserByAdmin_PositiveTest() {
        Allure.step("Step 1: Create user to be deleted");
        String editor = "admin";
        CreatePlayerRequestModel model = new CreatePlayerRequestModel();
        model.setLogin(TestDataGenerator.generateUniqueLogin());
        model.setScreenName(TestDataGenerator.generateUniqueScreenName());
        model.setPassword(TestDataGenerator.generateValidPassword());
        model.setGender(TestDataGenerator.getRandomGender());
        model.setAge(TestDataGenerator.generateValidAge());
        model.setRole("user");
        model.setEditor(editor);

        Response createResponse = PlayerApi.sendCreatePlayerRequest(model);
        Allure.addAttachment("Create Player Full Response", "text/plain",
                createResponse.statusCode() + "\n" +
                        createResponse.getHeaders().toString() + "\n" +
                        createResponse.asString());

        Assert.assertEquals(createResponse.statusCode(), 200, "User creation failed before deletion");
        Long playerId = createResponse.jsonPath().getLong("id");

        Allure.step("Step 2: Send delete request");
        DeletePlayerRequestModel deleteRequest = new DeletePlayerRequestModel();
        deleteRequest.setEditor(editor);
        deleteRequest.setPlayerId(playerId.intValue());
        Response deleteResponse = PlayerApi.sendDeletePlayerRequest(deleteRequest);
        Allure.addAttachment("Delete Player Full Response", "text/plain",
                deleteResponse.statusCode() + "\n" +
                        deleteResponse.getHeaders().toString() + "\n" +
                        deleteResponse.asString());

        Allure.step("Step 3: Validate delete response");
        Assert.assertEquals(deleteResponse.statusCode(), 204, "Expected 204 NO_CONTENT for successful deletion");

        Allure.step("Step 4: Verify the user does not exist anymore");
        GetPlayerByIdRequestModel getRequestModel = new GetPlayerByIdRequestModel();
        getRequestModel.setId(playerId);
        Response getResponse = PlayerApi.sendGetPlayerByIdRequest(getRequestModel);
        Allure.addAttachment("Get Player Full Response", "text/plain",
                getResponse.statusCode() + "\n" +
                        getResponse.getHeaders().toString() + "\n" +
                        getResponse.asString());

        Assert.assertEquals(getResponse.statusCode(), 404, "Expected 404 NOT_FOUND for deleted user");
    }
}
