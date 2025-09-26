package com.pavel.qa.tests.player.create;

import com.pavel.qa.base.BaseTest;
import com.pavel.qa.utils.TestDataGenerator;
import static com.pavel.qa.steps.TestDataSteps.*;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Epic("PlayerController")
@Feature("Player Creation")

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
    @Story("Positive Test - Valid User Creation with Valid Roles")
    @Severity(SeverityLevel.CRITICAL)
    public void createUserWithRole_PositiveTest(String editor, String role) {
        String login = generateLogin();
        String screenName = generateScreenName();
        String password = generatePassword();
        String gender = generateGender();
        String age = generateAge();

        Response response = sendCreatePlayerRequest(editor, age, gender, login, password, role, screenName);
        attachResponse(response);

        Assert.assertEquals(response.statusCode(), 200, "Expected 200 OK for valid user creation");
        Assert.assertTrue(response.asString().contains(login), "Response should contain login");
        Assert.assertTrue(response.asString().contains(role), "Response should contain role");
    }

    @Test(description = "Create user with minimum valid age")
    @Story("Positive Test - Minimum Age Boundary")
    @Severity(SeverityLevel.NORMAL)
    public void createUserWithMinimumValidAge_PositiveTest() {
        String editor = "supervisor";
        String login = TestDataGenerator.generateUniqueLogin();
        String screenName = "ValidPlayerMinAge";
        String password = TestDataGenerator.generateValidPassword();
        String role = "user";
        String gender = TestDataGenerator.getRandomGender();
        String age = "17"; // minimum valid age

        Response response = sendCreatePlayerRequest(editor, age, gender, login, password, role, screenName);
        attachResponse(response);

        Assert.assertEquals(response.statusCode(), 200, "Expected 200 OK for user with minimum valid age");
        Assert.assertTrue(response.asString().contains(login), "Response should contain login");
        Assert.assertTrue(response.asString().contains(role), "Response should contain role");
    }

    @Test(description = "Create user with maximum valid age")
    @Story("Positive Test - Maximum Age Boundary")
    @Severity(SeverityLevel.NORMAL)
    public void createUserWithMaximumValidAge_PositiveTest() {
        String editor = "supervisor";
        String login = TestDataGenerator.generateUniqueLogin();
        String screenName = "ValidPlayerMaxAge";
        String password = TestDataGenerator.generateValidPassword();
        String role = "user";
        String gender = TestDataGenerator.getRandomGender();
        String age = "59"; // maximum valid age

        Response response = sendCreatePlayerRequest(editor, age, gender, login, password, role, screenName);
        attachResponse(response);

        Assert.assertEquals(response.statusCode(), 200, "Expected 200 OK for user with maximum valid age");
        Assert.assertTrue(response.asString().contains(login), "Response should contain login");
        Assert.assertTrue(response.asString().contains(role), "Response should contain role");
    }

    @Test(description = "Validate JSON response structure for user creation")
    @Story("Positive Test - Validate Response Structure")
    @Severity(SeverityLevel.NORMAL)
    public void validateResponseStructure_PositiveTest() {
        String editor = "supervisor";
        String login = TestDataGenerator.generateUniqueLogin();
        String screenName = "ValidStructurePlayer";
        String password = TestDataGenerator.generateValidPassword();
        String role = "user";
        String gender = TestDataGenerator.getRandomGender();
        String age = TestDataGenerator.generateValidAge();

        Response response = sendCreatePlayerRequest(editor, age, gender, login, password, role, screenName);
        attachResponse(response);

        Assert.assertEquals(response.statusCode(), 200, "Expected 200 OK");

        JsonPath json = response.jsonPath();

        // Check if all the keys are present
        Assert.assertNotNull(json.get("age"), "Response should contain 'age'");
        Assert.assertNotNull(json.get("gender"), "Response should contain 'gender'");
        Assert.assertNotNull(json.get("id"), "Response should contain 'id'");
        Assert.assertNotNull(json.get("login"), "Response should contain 'login'");
        Assert.assertNotNull(json.get("password"), "Response should contain 'password'");
        Assert.assertNotNull(json.get("role"), "Response should contain 'role'");
        Assert.assertNotNull(json.get("screenName"), "Response should contain 'screenName'");


        // Check of the data types (optional)
        Assert.assertTrue(json.get("age") instanceof Integer, "age should be an integer");
        Assert.assertTrue(json.get("gender") instanceof String, "gender should be a string");
        Assert.assertTrue(json.get("id") instanceof Integer, "id should be an integer");
        Assert.assertTrue(json.get("login") instanceof String, "login should be a string");
        Assert.assertTrue(json.get("password") instanceof String, "password should be a string");
        Assert.assertTrue(json.get("role") instanceof String, "role should be a string");
        Assert.assertTrue(json.get("screenName") instanceof String, "screenName should be a string");
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
