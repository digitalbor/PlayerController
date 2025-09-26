package com.pavel.qa.tests.player.create;

import com.pavel.qa.base.BaseTest;
import com.pavel.qa.utils.TestDataGenerator;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
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
        String editor = "supervisor";
        String login = TestDataGenerator.generateUniqueLogin();
        String screenName = "YoungPlayer";
        String password = TestDataGenerator.generateValidPassword();
        String role = "user";
        String gender = TestDataGenerator.getRandomGender();
        String age = "15"; // Invalid age

        Allure.step("Step 2: Send create player request");
        Response response = sendCreatePlayerRequest(editor, age, gender, login, password, role, screenName);

        Allure.step("Step 3: Attach response to report");
        Allure.addAttachment("Create Player Response", "text/plain", response.asString());

        Allure.step("Step 4: Validate response");
        Assert.assertEquals(response.statusCode(), 400, "Expected 400 BAD_REQUEST for age < 16");
    }



    @Attachment(value = "Response Body", type = "text/plain")
    private String attachResponse(Response response) {
        return response.asString();
    }


    @Test(description = "Attempt to create player with invalid gender should return 400 BAD_REQUEST")
    @Story("Negative Test - Gender Validation")
    @Severity(SeverityLevel.NORMAL)
    public void createUserWithInvalidGender_ShouldReturnBadRequest() {
        Allure.step("Step 1: Generate test data");
        String editor = "supervisor";
        String login = "invalidGenderUser";
        String screenName = "InvalidGenderPlayer";
        String password = "abc1234";
        String role = "user";
        String gender = "unknown"; // Invalid gender
        String age = "25";

        Allure.step("Step 2: Send create player request");
        Response response = sendCreatePlayerRequest(editor, age, gender, login, password, role, screenName);

        Allure.step("Step 3: Attach response to report");
        Allure.addAttachment("Create Player Response", "text/plain", response.asString());

        Allure.step("Step 4: Validate response");
        Assert.assertEquals(response.statusCode(), 400, "Expected 400 BAD_REQUEST for invalid gender");
    }

    public Response sendCreatePlayerRequest(String editor, String age, String gender, String login, String password, String role, String screenName) {
        return RestAssured
                .given()
                .queryParam("age", age)
                .queryParam("gender", gender)
                .queryParam("login", login)
                .queryParam("password", password)
                .queryParam("role", role)
                .queryParam("screenName", screenName)
                .get("/player/create/" + editor);
    }

}
