package com.pavel.qa.utils;

import com.pavel.qa.tests.player.models.CreatePlayerRequestModel;
import com.pavel.qa.tests.player.models.DeletePlayerRequestModel;
import com.pavel.qa.tests.player.models.GetPlayerByIdRequestModel;
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


    public static Response sendDeletePlayerRequest(DeletePlayerRequestModel model) {

        Map<String, String> body = new HashMap<>();
        body.put("playerId",String.valueOf(model));

        return RestAssured
                .given()
                .header("accept", "*/*")
                .header("Content-Type", "application/json")
                .body(body)
                .delete("/player/delete/" + model.getEditor());
    }


    public static Response sendGetPlayerByIdRequest(GetPlayerByIdRequestModel model) {

        Map<String, String> body = new HashMap<>();
        body.put("playerId", String.valueOf(model.getId()));

        return RestAssured
                .given()
                .header("accept", "*/*")
                .header("Content-Type", "application/json")
                .body(body)
                .post("/player/get");
    }


}
