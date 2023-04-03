package com.example.snake.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameStateObject implements Serializable {
    private List<SerializableSnake> snakeList;

    public GameStateObject() {
        snakeList = new ArrayList<>();
    }

    public List<SerializableSnake> getSnakeList() {
        return snakeList;
    }

    public void setSnakeList(List<SerializableSnake> snakeList) {
        this.snakeList = snakeList;
    }
}
