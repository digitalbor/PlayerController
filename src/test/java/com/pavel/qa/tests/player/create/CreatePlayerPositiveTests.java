package com.pavel.qa.tests.player.create;

import com.pavel.qa.base.BaseTest;
import com.pavel.qa.utils.TestDataGenerator;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Player Creation")
@Story("Positive Test - Valid User Creation")
@Severity(SeverityLevel.CRITICAL)
public class CreatePlayerPositiveTests extends BaseTest {

    @Test(description = "Create user with valid data as admin")
    public void createUserAsAdmin_PositiveTest() {
        String editor = "supervisor";
        String login = TestDataGenerator.generateUniqueLogin();
        String screenName = "ValidPlayer";
        String password = TestDataGenerator.generateValidPassword();
        String role = "user";
        String gender = TestDataGenerator.getRandomGender();
        String age = TestDataGenerator.generateValidAge();

        Response response = sendCreatePlayerRequest(editor, age, gender, login, password, role, screenName);
        attachResponse(response);

        Assert.assertEquals(response.statusCode(), 200, "Expected 200 OK for valid user creation");
        Assert.assertTrue(response.asString().contains(login), "Response should contain login");
        Assert.assertTrue(response.asString().contains(role), "Response should contain role");
    }

    @Step("Send create player request with editor={editor}, age={age}, gender={gender}, login={login}, password={password}, role={role}, screenName={screenName}")
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

    @Attachment(value = "Create Player Response", type = "text/plain")
    public String attachResponse(Response response) {
        return response.asString();
    }
}

