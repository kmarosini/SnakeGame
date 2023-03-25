package com.example.snake.models;

import javafx.geometry.Pos;

import java.io.Serializable;
import java.util.List;

public class SerializableSnake implements Serializable {
    private double positionX;
    private double positionY;
    private int snakeSize;


    public SerializableSnake() {
    }

    public SerializableSnake(double positionX, double positionY, int snakeSize) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.snakeSize = snakeSize;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public int getSnakeSize() {
        return snakeSize;
    }

    public void setSnakeSize(int snakeSize) {
        this.snakeSize = snakeSize;
    }
}
