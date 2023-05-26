package com.example.snake.models;

import java.io.Serializable;

public class Position implements Serializable {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Position() {
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public String toString() {
        return x + "," + y;
    }
}
