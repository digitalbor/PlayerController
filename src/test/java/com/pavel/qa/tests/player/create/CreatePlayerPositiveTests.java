package com.pavel.qa.tests.player.create;

import com.pavel.qa.base.BaseTest;
import com.pavel.qa.utils.CommonAssertions;
import com.pavel.qa.utils.CreatePlayerRequestModel;
import com.pavel.qa.utils.CreatePlayerResponseModel;
import com.pavel.qa.utils.PlayerApi;
import com.pavel.qa.utils.TestDataGenerator;
import io.qameta.allure.Allure;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
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
        CreatePlayerRequestModel model = new CreatePlayerRequestModel();
        model.setLogin(TestDataGenerator.generateUniqueLogin());
        model.setScreenName(TestDataGenerator.generateUniqueScreenName());
        model.setPassword(TestDataGenerator.generateValidPassword());
        model.setGender(TestDataGenerator.getRandomGender());
        model.setAge(TestDataGenerator.generateValidAge());
        model.setRole(role);
        model.setEditor(editor);

        Allure.step("Step 2: Send create player request");
        Response response = PlayerApi.sendCreatePlayerRequest(model);
        Allure.addAttachment("Create Player Full Response", "text/plain", response.statusCode() + "\n" + response.getHeaders().toString() + "\n" + response.asString());

        Allure.step("Step 3: Validate response");
        Assert.assertEquals(response.statusCode(), 200, "Expected 200 OK for valid user creation");

        CreatePlayerResponseModel responseModel = response.as(CreatePlayerResponseModel.class);
        Assert.assertEquals(responseModel.getLogin(), model.getLogin(), "Login should match");
        Assert.assertEquals(responseModel.getRole(), model.getRole(), "Role should match");
        Assert.assertEquals(responseModel.getScreenName(), model.getScreenName(), "ScreenName should match");
        Assert.assertEquals(responseModel.getGender(), model.getGender(), "Gender should match");
        Assert.assertEquals(responseModel.getAge(), Integer.parseInt(model.getAge()), "Age should match");
        CommonAssertions.validateCreatePlayerResponse(responseModel);

        Allure.step("Step 4: Verify player data via GET request");
        Response getResponse = PlayerApi.sendGetPlayerByIdRequest((long) responseModel.getId());
        Allure.addAttachment("Get Player Full Response", "text/plain", getResponse.statusCode() + "\n" + getResponse.getHeaders().toString() + "\n" + getResponse.asString());
        Assert.assertEquals(getResponse.statusCode(), 200, "Expected 200 OK for get player");

        CreatePlayerResponseModel getModel = getResponse.as(CreatePlayerResponseModel.class);
        Assert.assertEquals(getModel.getLogin(), model.getLogin(), "Login should match");
        Assert.assertEquals(getModel.getRole(), model.getRole(), "Role should match");
        Assert.assertEquals(getModel.getScreenName(), model.getScreenName(), "ScreenName should match");
        Assert.assertEquals(getModel.getGender(), model.getGender(), "Gender should match");
        Assert.assertEquals(getModel.getAge(), Integer.parseInt(model.getAge()), "Age should match");
    }

    @Test(description = "Create user with minimum valid age")
    @Description("Verify that Valid User with Minimum valid age can create a user")
    @Story("Positive Test - Minimum Age Boundary")
    @Severity(SeverityLevel.NORMAL)
    public void createUserWithMinimumValidAge_PositiveTest() {
        Allure.step("Step 1: Generate test data");
        CreatePlayerRequestModel model = new CreatePlayerRequestModel();
        model.setLogin(TestDataGenerator.generateUniqueLogin());
        model.setScreenName("ValidPlayerMinAge");
        model.setPassword(TestDataGenerator.generateValidPassword());
        model.setGender(TestDataGenerator.getRandomGender());
        model.setAge("17"); // minimum valid age
        model.setRole("user");
        model.setEditor("supervisor");

        Allure.step("Step 2: Send create player request");
        Response response = PlayerApi.sendCreatePlayerRequest(model);
        Allure.addAttachment("Create Player Full Response", "text/plain", response.statusCode() + "\n" + response.getHeaders().toString() + "\n" + response.asString());

        Allure.step("Step 3: Validate response");
        Assert.assertEquals(response.statusCode(), 200, "Expected 200 OK for user with minimum valid age");

        CreatePlayerResponseModel responseModel = response.as(CreatePlayerResponseModel.class);
        Assert.assertEquals(responseModel.getLogin(), model.getLogin(), "Login should match");
        Assert.assertEquals(responseModel.getRole(), model.getRole(), "Role should match");
        Assert.assertEquals(responseModel.getScreenName(), model.getScreenName(), "ScreenName should match");
        Assert.assertEquals(responseModel.getGender(), model.getGender(), "Gender should match");
        Assert.assertEquals(responseModel.getAge(), Integer.parseInt(model.getAge()), "Age should match");
        CommonAssertions.validateCreatePlayerResponse(responseModel);

        Allure.step("Step 4: Verify player data via GET request");
        Response getResponse = PlayerApi.sendGetPlayerByIdRequest((long) responseModel.getId());
        Allure.addAttachment("Get Player Full Response", "text/plain", getResponse.statusCode() + "\n" + getResponse.getHeaders().toString() + "\n" + getResponse.asString());
        Assert.assertEquals(getResponse.statusCode(), 200, "Expected 200 OK for get player");

        CreatePlayerResponseModel getModel = getResponse.as(CreatePlayerResponseModel.class);
        Assert.assertEquals(getModel.getLogin(), model.getLogin(), "Login should match");
        Assert.assertEquals(getModel.getRole(), model.getRole(), "Role should match");
        Assert.assertEquals(getModel.getScreenName(), model.getScreenName(), "ScreenName should match");
        Assert.assertEquals(getModel.getGender(), model.getGender(), "Gender should match");
        Assert.assertEquals(getModel.getAge(), Integer.parseInt(model.getAge()), "Age should match");
    }

    @Test(description = "Create user with maximum valid age")
    @Story("Positive Test - Maximum Age Boundary")
    @Severity(SeverityLevel.NORMAL)
    public void createUserWithMaximumValidAge_PositiveTest() {
        Allure.step("Step 1: Generate test data");
        CreatePlayerRequestModel model = new CreatePlayerRequestModel();
        model.setLogin(TestDataGenerator.generateUniqueLogin());
        model.setScreenName("ValidPlayerMaxAge");
        model.setPassword(TestDataGenerator.generateValidPassword());
        model.setGender(TestDataGenerator.getRandomGender());
        model.setAge("59"); // maximum valid age
        model.setRole("user");
        model.setEditor("supervisor");

        Allure.step("Step 2: Send create player request");
        Response response = PlayerApi.sendCreatePlayerRequest(model);
        Allure.addAttachment("Create Player Full Response", "text/plain", response.statusCode() + "\n" + response.getHeaders().toString() + "\n" + response.asString());

        Allure.step("Step 3: Validate response");
        Assert.assertEquals(response.statusCode(), 200, "Expected 200 OK for user with maximum valid age");

        CreatePlayerResponseModel responseModel = response.as(CreatePlayerResponseModel.class);
        Assert.assertEquals(responseModel.getLogin(), model.getLogin(), "Login should match");
        Assert.assertEquals(responseModel.getRole(), model.getRole(), "Role should match");
        Assert.assertEquals(responseModel.getScreenName(), model.getScreenName(), "ScreenName should match");
        Assert.assertEquals(responseModel.getGender(), model.getGender(), "Gender should match");
        Assert.assertEquals(responseModel.getAge(), Integer.parseInt(model.getAge()), "Age should match");
        CommonAssertions.validateCreatePlayerResponse(responseModel);

        Allure.step("Step 4: Verify player data via GET request");
        Response getResponse = PlayerApi.sendGetPlayerByIdRequest((long) responseModel.getId());
        Allure.addAttachment("Get Player Full Response", "text/plain", getResponse.statusCode() + "\n" + getResponse.getHeaders().toString() + "\n" + getResponse.asString());
        Assert.assertEquals(getResponse.statusCode(), 200, "Expected 200 OK for get player");

        CreatePlayerResponseModel getModel = getResponse.as(CreatePlayerResponseModel.class);
        Assert.assertEquals(getModel.getLogin(), model.getLogin(), "Login should match");
        Assert.assertEquals(getModel.getRole(), model.getRole(), "Role should match");
        Assert.assertEquals(getModel.getScreenName(), model.getScreenName(), "ScreenName should match");
        Assert.assertEquals(getModel.getGender(), model.getGender(), "Gender should match");
        Assert.assertEquals(getModel.getAge(), Integer.parseInt(model.getAge()), "Age should match");
    }
}
