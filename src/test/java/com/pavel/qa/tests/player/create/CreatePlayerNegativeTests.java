package com.pavel.qa.tests.player.create;

import com.pavel.qa.base.BaseTest;
import com.pavel.qa.utils.CreatePlayerRequestModel;
import com.pavel.qa.utils.PlayerApi;
import com.pavel.qa.utils.TestDataGenerator;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.Test;

@Epic("PlayerController")
@Feature("Player Creation")
@Tag("negative")
public class CreatePlayerNegativeTests extends BaseTest {

    @Test(description = "Attempt to create player with age < 16 should return 400 BAD_REQUEST")
    @Description("Verify that Supervisor can not create a user with invalid too young age")
    @Story("Negative Test - Age Validation")
    @Severity(SeverityLevel.CRITICAL)
    public void createUserWithTooYoungAge_ShouldReturnBadRequest() {
        Allure.step("Step 1: Generate test data");
        CreatePlayerRequestModel model = new CreatePlayerRequestModel();
        model.setEditor("supervisor");
        model.setLogin(TestDataGenerator.generateUniqueLogin());
        model.setScreenName(TestDataGenerator.generateUniqueScreenName());
        model.setPassword(TestDataGenerator.generateValidPassword());
        model.setGender(TestDataGenerator.getRandomGender());
        model.setAge("15"); // Invalid age
        model.setRole("user");

        Allure.step("Step 2: Send create player request");
        Response response = PlayerApi.sendCreatePlayerRequest(model);
        Allure.addAttachment("Create Player Full Response", "text/plain", response.statusCode() + "\n" + response.getHeaders().toString() + "\n" + response.asString());

        Allure.step("Step 3: Validate response");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 400, "Expected 400 BAD_REQUEST for age < 16");
        softAssert.assertAll();
    }

    @Test(description = "Attempt to create player with invalid gender should return 400 BAD_REQUEST")
    @Story("Negative Test - Gender Validation")
    @Severity(SeverityLevel.NORMAL)
    public void createUserWithInvalidGender_ShouldReturnBadRequest() {
        Allure.step("Step 1: Generate test data");
        CreatePlayerRequestModel model = new CreatePlayerRequestModel();
        model.setLogin(TestDataGenerator.generateUniqueLogin());
        model.setScreenName(TestDataGenerator.generateUniqueScreenName());
        model.setPassword(TestDataGenerator.generateValidPassword());
        model.setGender("unknown"); // Invalid gender
        model.setAge(TestDataGenerator.generateValidAge());
        model.setRole("user");
        model.setEditor("supervisor");

        Allure.step("Step 2: Send create player request");
        Response response = PlayerApi.sendCreatePlayerRequest(model);
        Allure.addAttachment("Create Player Full Response", "text/plain", response.statusCode() + "\n" + response.getHeaders().toString() + "\n" + response.asString());

        Allure.step("Step 3: Validate response");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 400, "Expected 400 BAD_REQUEST for invalid gender");
        softAssert.assertAll();
    }
}
