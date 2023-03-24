package com.example.snake;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;

public class HelloController {
    public void startGame() throws IOException {
        sceneSwitcher("game-view.fxml", 930, 580);
    }


    public static void sceneSwitcher(String name, int v, int v1) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(name));
        Scene scene = new Scene(fxmlLoader.load(), v, v1);
        System.out.println(scene);
        HelloApplication.getMainStage().setTitle("Snake game!");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    public void exitApplication() throws IOException {
        HelloApplication.getMainStage().close();
    }
}