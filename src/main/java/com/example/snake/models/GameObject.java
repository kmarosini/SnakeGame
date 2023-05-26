package com.example.snake.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameObject implements Serializable {

    private List<Position> snake;
    private List<Replay> replayList;
    private Position startPosition;
    private SnakeSize size;
    private Food food;
    private int speed;
    private int score;
    private int directionNum;


    public GameObject() {
        this.snake = new ArrayList<>();;
        this.replayList = new ArrayList<>();
        this.startPosition = new Position();
        this.size = new SnakeSize();
        this.food = new Food();
        this.speed = 3;
        this.directionNum = -1;
        this.score = 0;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getDirectionNum() {
        return directionNum;
    }

    public void setDirectionNum(int directionNum) {
        this.directionNum = directionNum;
    }

    public List<Position> getSnake() {
        return snake;
    }

    public void setSnake(List<Position> snake) {
        this.snake = snake;
    }

    public List<Replay> getReplayList() {
        return replayList;
    }

    public void setReplayList(List<Replay> replayList) {
        this.replayList = replayList;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    public SnakeSize getSize() {
        return size;
    }

    public void setSize(SnakeSize size) {
        this.size = size;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
