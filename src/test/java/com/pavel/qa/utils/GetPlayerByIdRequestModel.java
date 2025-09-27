package com.pavel.qa.utils;

public class GetPlayerByIdRequestModel {
    private Long playerId;

    public GetPlayerByIdRequestModel() {}

    public GetPlayerByIdRequestModel(Long playerId) {
        this.playerId = playerId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }
}
