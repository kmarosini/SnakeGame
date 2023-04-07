package com.example.snake.models;

public class PlayerDetails {
    private String playerName;

    public PlayerDetails() {
    }

    public PlayerDetails(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
