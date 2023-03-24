package com.example.snake.models;

import java.util.List;

public class SnakeLenght {
    private int snakeSize;
    private Direction direction;

    public int getSnakeSize() {
        return snakeSize;
    }

    public void setSnakeSize(int snakeSize) {
        this.snakeSize = snakeSize;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public SnakeLenght() {
    }

    @Override
    public String toString() {
        return snakeSize + "," + direction;
    }

    public void snakeLenght(List<Position> snake) {
        for (int i = snake.size() - 1; i >= 1; i--) {
            snake.get(i).setX(snake.get(i - 1).getX());
            snake.get(i).setY(snake.get(i - 1).getY());
        }
    }
}
