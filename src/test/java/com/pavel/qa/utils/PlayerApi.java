package com.pavel.qa.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class PlayerApi {

    public static Response sendCreatePlayerRequest(String editor, String age, String gender, String login, String password, String role, String screenName) {
        return RestAssured
                .given()
                .header("accept", "*/*")
                .queryParam("age", age)
                .queryParam("gender", gender)
                .queryParam("login", login)
                .queryParam("password", password)
                .queryParam("role", role)
                .queryParam("screenName", screenName)
                .get("/player/create/" + editor);
    }


    public static Response sendDeletePlayerRequest(String editor, String playerId) {

        Map<String, String> body = new HashMap<>();
        body.put("playerId", playerId);

        return RestAssured
                .given()
                .header("accept", "*/*")
                .header("Content-Type", "application/json")
                .body(body)
                .delete("/player/delete/" + editor);
    }

}
