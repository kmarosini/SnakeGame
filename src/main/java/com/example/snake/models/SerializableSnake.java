package com.example.snake.models;

import java.io.Serializable;

public class SerializableSnake implements Serializable {
    private double positionX;
    private double positionY;
    private int snakeSize;
    private Direction direction;
    private String score;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public SerializableSnake() {
    }

    public SerializableSnake(double positionX, double positionY, int snakeSize, Direction direction, String score) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.snakeSize = snakeSize;
        this.direction = direction;
        this.score = score;
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
