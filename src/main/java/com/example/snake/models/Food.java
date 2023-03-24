package com.example.snake.models;

import javafx.scene.image.Image;

public class Food {
    private int fX;
    private int fY;
    private int fColor;
    private int score;

    public int getfColor() {
        return fColor;
    }

    public void setfColor(int fColor) {
        this.fColor = fColor;
    }

    public int getfX() {
        return fX;
    }

    public void setfX(int fX) {
        this.fX = fX;
    }

    public int getfY() {
        return fY;
    }

    public void setfY(int fY) {
        this.fY = fY;
    }

    public Food() {
    }

    @Override
    public String toString() {
        return fX + "," + fY + "," + fColor;
    }
}
