package com.pavel.qa.tests.player.create;

import com.pavel.qa.base.BaseTest;
import com.pavel.qa.utils.CommonAssertions;
import com.pavel.qa.utils.TestDataGenerator;
import io.qameta.allure.Allure;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Epic("PlayerController")
@Feature("Player Creation")
@Tag("positive")
public class CreatePlayerPositiveTests extends BaseTest {

    @DataProvider(name = "validEditorAndRole")
    public Object[][] validEditorAndRole() {
        return new Object[][] {
                {"admin", "user"},
                {"supervisor", "user"},
                {"admin", "admin"},
                {"supervisor", "admin"}
        };
    }

    @Test(description = "Create user with valid data", dataProvider = "validEditorAndRole")
    @Description("Verify that Valid User can create a user with valid Role")
    @Story("Positive Test - Valid User Creation with Valid Roles")
    @Severity(SeverityLevel.CRITICAL)
    public void createUserWithRole_PositiveTest(String editor, String role) {
        Allure.step("Step 1: Generate test data");
        String login = TestDataGenerator.generateUniqueLogin();
        String screenName = TestDataGenerator.generateUniqueScreenName();
        String password = TestDataGenerator.generateValidPassword();
        String gender = TestDataGenerator.getRandomGender();
        String age = TestDataGenerator.generateValidAge();

        Allure.step("Step 2: Send create player request");
        Response response = sendCreatePlayerRequest(editor, age, gender, login, password, role, screenName);

        Allure.step("Step 3: Attach response to report");
        Allure.addAttachment("Create Player Response", "text/plain", response.asString());

        Allure.step("Step 4: Validate response");
        Assert.assertEquals(response.statusCode(), 200, "Expected 200 OK for valid user creation");
        Assert.assertTrue(response.asString().contains(login), "Response should contain login");
        Assert.assertTrue(response.asString().contains(role), "Response should contain role");

        CommonAssertions.validateCreateUserJsonStructure(response);
    }

    @Test(description = "Create user with minimum valid age")
    @Description("Verify that Valid User with Minimum valid age can create a user")
    @Story("Positive Test - Minimum Age Boundary")
    @Severity(SeverityLevel.NORMAL)
    public void createUserWithMinimumValidAge_PositiveTest() {
        Allure.step("Step 1: Generate test data");
        String editor = "supervisor";
        String login = TestDataGenerator.generateUniqueLogin();
        String screenName = "ValidPlayerMinAge";
        String password = TestDataGenerator.generateValidPassword();
        String role = "user";
        String gender = TestDataGenerator.getRandomGender();
        String age = "17"; // minimum valid age

        Allure.step("Step 2: Send create player request");
        Response response = sendCreatePlayerRequest(editor, age, gender, login, password, role, screenName);

        Allure.step("Step 3: Attach response to report");
        Allure.addAttachment("Create Player Response", "text/plain", response.asString());

        Allure.step("Step 4: Validate response");
        Assert.assertEquals(response.statusCode(), 200, "Expected 200 OK for user with minimum valid age");
        Assert.assertTrue(response.asString().contains(login), "Response should contain login");
        Assert.assertTrue(response.asString().contains(role), "Response should contain role");

        CommonAssertions.validateCreateUserJsonStructure(response);
    }

    @Test(description = "Create user with maximum valid age")
    @Story("Positive Test - Maximum Age Boundary")
    @Severity(SeverityLevel.NORMAL)
    public void createUserWithMaximumValidAge_PositiveTest() {
        Allure.step("Step 1: Generate test data");
        String editor = "supervisor";
        String login = TestDataGenerator.generateUniqueLogin();
        String screenName = "ValidPlayerMaxAge";
        String password = TestDataGenerator.generateValidPassword();
        String role = "user";
        String gender = TestDataGenerator.getRandomGender();
        String age = "59"; // maximum valid age

        Allure.step("Step 2: Send create player request");
        Response response = sendCreatePlayerRequest(editor, age, gender, login, password, role, screenName);

        Allure.step("Step 3: Attach response to report");
        Allure.addAttachment("Create Player Response", "text/plain", response.asString());

        Allure.step("Step 4: Validate response");
        Assert.assertEquals(response.statusCode(), 200, "Expected 200 OK for user with maximum valid age");
        Assert.assertTrue(response.asString().contains(login), "Response should contain login");
        Assert.assertTrue(response.asString().contains(role), "Response should contain role");

        CommonAssertions.validateCreateUserJsonStructure(response);
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
