package com.pavel.qa.tests.player.delete;

import com.pavel.qa.base.BaseTest;
import com.pavel.qa.utils.CreatePlayerRequestModel;
import com.pavel.qa.utils.PlayerApi;
import com.pavel.qa.utils.TestDataGenerator;
import io.qameta.allure.Allure;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("PlayerController")
@Feature("Player Deletion")
@Tag("negative")
public class DeletePlayerNegativeTests extends BaseTest {

    @Test(description = "Attempt to delete admin by another admin should return 403_FORBIDDEN")
    @Description("Verify that an admin cannot delete another admin")
    @Story("Negative Test - Admin cannot delete another admin")
    @Severity(SeverityLevel.CRITICAL)
    public void adminCannotDeleteAnotherAdmin_NegativeTest() {
        Allure.step("Step 1: Create a second admin user to be deleted");
        String login = TestDataGenerator.generateUniqueLogin();
        String screenName = TestDataGenerator.generateUniqueScreenName();
        String password = TestDataGenerator.generateValidPassword();
        String gender = TestDataGenerator.getRandomGender();
        String age = TestDataGenerator.generateValidAge();
        String role = "admin";
        String creator = "supervisor"; // only supervisor can create admins

        CreatePlayerRequestModel model = new CreatePlayerRequestModel();
        model.setLogin(login);
        model.setScreenName(screenName);
        model.setPassword(password);
        model.setGender(gender);
        model.setAge(age);
        model.setRole(role);

        Response createResponse = PlayerApi.sendCreatePlayerRequest(model);
        Allure.addAttachment("Create Admin Response", "text/plain", createResponse.asString());
        Assert.assertEquals(createResponse.statusCode(), 200, "Admin creation failed before deletion attempt");

        String playerId = createResponse.jsonPath().getString("id");

        Allure.step("Step 2: Attempt to delete admin user using another admin");
        Response deleteResponse = PlayerApi.sendDeletePlayerRequest("admin", playerId);
        Allure.addAttachment("Delete Attempt Response", "text/plain", deleteResponse.asString());

        Allure.step("Step 3: Validate that deletion is forbidden");
        Assert.assertEquals(deleteResponse.statusCode(), 403, "Expected 403 Forbidden when admin tries to delete another admin");
    }
}
