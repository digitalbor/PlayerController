package com.pavel.qa.tests.player.delete;

import com.pavel.qa.base.BaseTest;
import com.pavel.qa.models.*;
import com.pavel.qa.utils.PlayerApi;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.Test;
import com.pavel.qa.generators.TestDataGenerator;

@Epic("PlayerController")
@Feature("Player Deletion")
@Tag("negative")
public class DeletePlayerNegativeTests extends BaseTest {

    @Test(description = "Attempt to delete admin by another admin should return 403_FORBIDDEN")
    @Description("Verify that an admin cannot delete another admin")
    @Story("Negative Test - Admin cannot delete another admin")
    @Severity(SeverityLevel.CRITICAL)
    public void adminCannotDeleteAnotherAdmin_NegativeTest() {
        SoftAssert softAssert = new SoftAssert();

        Allure.step("Step 1: Create admin user to be deleted");
        CreatePlayerRequestModel targetAdmin = new CreatePlayerRequestModel();
        targetAdmin.setLogin(TestDataGenerator.generateUniqueLogin());
        targetAdmin.setScreenName(TestDataGenerator.generateUniqueScreenName());
        targetAdmin.setPassword(TestDataGenerator.generateValidPassword());
        targetAdmin.setGender(TestDataGenerator.getRandomGender());
        targetAdmin.setAge(TestDataGenerator.generateValidAge());
        targetAdmin.setRole("admin");
        targetAdmin.setEditor("supervisor");

        Response createTargetResponse = PlayerApi.sendCreatePlayerRequest(targetAdmin);
        Allure.addAttachment("Create Target Admin Full Response", "text/plain", createTargetResponse.statusCode() + "\n" + createTargetResponse.getHeaders().toString() + "\n" + createTargetResponse.asString());
        softAssert.assertEquals(createTargetResponse.statusCode(), 200, "Target admin creation failed");
        CreatePlayerResponseModel targetResponseModel = createTargetResponse.as(CreatePlayerResponseModel.class);
        Long targetPlayerId = targetResponseModel.getId();


        Allure.step("Step 2: Create second admin to perform deletion");
        CreatePlayerRequestModel editorAdmin = new CreatePlayerRequestModel();
        editorAdmin.setLogin(TestDataGenerator.generateUniqueLogin());
        editorAdmin.setScreenName(TestDataGenerator.generateUniqueScreenName());
        editorAdmin.setPassword(TestDataGenerator.generateValidPassword());
        editorAdmin.setGender(TestDataGenerator.getRandomGender());
        editorAdmin.setAge(TestDataGenerator.generateValidAge());
        editorAdmin.setRole("admin");
        editorAdmin.setEditor("supervisor");

        Response createEditorResponse = PlayerApi.sendCreatePlayerRequest(editorAdmin);
        Allure.addAttachment("Create Editor Admin Full Response", "text/plain", createEditorResponse.statusCode() + "\n" + createEditorResponse.getHeaders().toString() + "\n" + createEditorResponse.asString());
        softAssert.assertEquals(createEditorResponse.statusCode(), 200, "Editor admin creation failed");
        String editorLogin = editorAdmin.getLogin();

        Allure.step("Step 3: Attempt to delete target admin using editor admin");
        DeletePlayerRequestModel deleteRequest = new DeletePlayerRequestModel();
        deleteRequest.setPlayerId(targetPlayerId.intValue());

        Response deleteResponse = PlayerApi.sendDeletePlayerRequest(editorLogin, deleteRequest);
        Allure.addAttachment("Delete Attempt Full Response", "text/plain", deleteResponse.statusCode() + "\n" + deleteResponse.getHeaders().toString() + "\n" + deleteResponse.asString());
        softAssert.assertEquals(deleteResponse.statusCode(), 403, "Expected 403 Forbidden when admin tries to delete another admin");

        softAssert.assertAll();
    }
}
