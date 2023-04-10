package com.example.snake.models;

import java.util.List;

public class SnakeSize {
    private int SnakeLength;
    private Direction direction;

    public int getSnakeLength() {
        return SnakeLength;
    }

    public void setSnakeLength(int snakeLength) {
        this.SnakeLength = snakeLength;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public SnakeSize() {
    }

    @Override
    public String toString() {
        return SnakeLength + "," + direction;
    }

    public void snakeLenght(List<Position> snake) {
        for (int i = snake.size() - 1; i >= 1; i--) {
            snake.get(i).setX(snake.get(i - 1).getX());
            snake.get(i).setY(snake.get(i - 1).getY());
        }
    }
}
