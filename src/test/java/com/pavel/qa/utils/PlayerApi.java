package com.pavel.qa.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class PlayerApi {

    public static Response sendCreatePlayerRequest(CreatePlayerRequestModel model) {
        return RestAssured
                .given()
                .header("accept", "*/*")
                .queryParam("age", model.getAge())
                .queryParam("gender", model.getGender())
                .queryParam("login", model.getLogin())
                .queryParam("password", model.getPassword())
                .queryParam("role", model.getRole())
                .queryParam("screenName", model.getScreenName())
                .get("/player/create/" + model.getEditor());
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


    public static Response sendGetPlayerByIdRequest(Long playerId) {
        String jsonBody = "{ \"playerId\": \"" + playerId + "\" }";

        return RestAssured
                .given()
                .header("accept", "*/*")
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .post("/player/get");
    }

    public static Response sendGetPlayersAllRequest() {

        return  RestAssured
                .given()
                .header("accept", "*/*")
                .get("/player/get/all");
    }

}
