package com.example.snake.models;

public class Replay {
    private double positionX;
    private double positionY;
    private double foodX;
    private double foodY;
    private String score;
    private Direction direction;

    public Replay(double positionX, double positionY, double foodX, double foodY, String score, Direction direction) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.foodX = foodX;
        this.foodY = foodY;
        this.score = score;
        this.direction = direction;
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

    public double getFoodX() {
        return foodX;
    }

    public void setFoodX(double foodX) {
        this.foodX = foodX;
    }

    public double getFoodY() {
        return foodY;
    }

    public void setFoodY(double foodY) {
        this.foodY = foodY;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
