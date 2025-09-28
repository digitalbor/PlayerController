package com.pavel.qa.tests.player.delete;

import com.pavel.qa.base.BaseTest;
import com.pavel.qa.utils.*;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.Test;
import com.pavel.qa.utils.TestDataGenerator;

@Epic("PlayerController")
@Feature("Player Deletion")
@Tag("negative")
public class DeletePlayerNegativeTests extends BaseTest {

    @Test(description = "Attempt to delete admin by another admin should return 403_FORBIDDEN")
    @Description("Verify that an admin cannot delete another admin")
    @Story("Negative Test - Admin cannot delete another admin")
    @Severity(SeverityLevel.CRITICAL)
    public void adminCannotDeleteAnotherAdmin_NegativeTest() {

        Allure.step("Step 1: Create user to be deleted");
        CreatePlayerRequestModel model = new CreatePlayerRequestModel();
        model.setLogin(TestDataGenerator.generateUniqueLogin());
        model.setScreenName(TestDataGenerator.generateUniqueScreenName());
        model.setPassword(TestDataGenerator.generateValidPassword());
        model.setGender(TestDataGenerator.getRandomGender());
        model.setAge(TestDataGenerator.generateValidAge());
        model.setRole("admin");
        model.setEditor("supervisor"); // only supervisor can create admins

        Response createResponse = PlayerApi.sendCreatePlayerRequest(model);
        Allure.addAttachment("Create Admin Full Response", "text/plain",
                createResponse.statusCode() + "\n" +
                        createResponse.getHeaders().toString() + "\n" +
                        createResponse.asString());

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(createResponse.statusCode(), 200, "Admin creation failed before deletion attempt");
        Long playerId = createResponse.jsonPath().getLong("id");

        Allure.step("Step 2: Attempt to delete admin user using another admin");
        DeletePlayerRequestModel deleteRequest = new DeletePlayerRequestModel();
        deleteRequest.setEditor("admin");
        deleteRequest.setPlayerId(playerId.intValue());

        Response deleteResponse = PlayerApi.sendDeletePlayerRequest(deleteRequest);
        Allure.addAttachment("Delete Attempt Full Response", "text/plain",
                deleteResponse.statusCode() + "\n" +
                        deleteResponse.getHeaders().toString() + "\n" +
                        deleteResponse.asString());

        Allure.step("Step 3: Validate that deletion is forbidden");
        softAssert.assertEquals(deleteResponse.statusCode(), 403, "Expected 403 Forbidden when admin tries to delete another admin");
        softAssert.assertAll();
    }
}
