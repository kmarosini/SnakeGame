package com.example.snake.models;

public class Message {
    private String playerName;
    private String message;

    public Message(String playerName, String message) {
        this.playerName = playerName;
        this.message = message;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
