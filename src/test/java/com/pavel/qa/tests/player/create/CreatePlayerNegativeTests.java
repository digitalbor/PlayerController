package com.pavel.qa.tests.player.create;

import com.pavel.qa.base.BaseTest;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Player Creation")
@Story("Negative Test - Age Validation")
@Severity(SeverityLevel.CRITICAL)
public class CreatePlayerNegativeTests extends BaseTest {

    @Test(description = "Attempt to create player with age < 16 should return 400 BAD_REQUEST")
    public void createUserWithTooYoungAge_ShouldReturnBadRequest() {
        String editor = "supervisor";
        String login = "youngUser123";
        String screenName = "YoungPlayer";
        String password = "abc1234";
        String role = "user";
        String gender = "male";
        String age = "15"; // Invalid age

        Response response = sendCreatePlayerRequest(editor, age, gender, login, password, role, screenName);
        attachResponse(response);
        Assert.assertEquals(response.statusCode(), 400, "Expected 400 BAD_REQUEST for age < 16");
    }

    @Step("Send create player request with age={age}, gender={gender}, login={login}, password={password}, role={role}, screenName={screenName}")
    private Response sendCreatePlayerRequest(String editor, String age, String gender, String login, String password, String role, String screenName) {
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

    @Attachment(value = "Response Body", type = "text/plain")
    private String attachResponse(Response response) {
        return response.asString();
    }
}
