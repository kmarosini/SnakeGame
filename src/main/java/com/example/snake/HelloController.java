package com.example.snake;

import com.example.snake.models.PlayerDetails;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class HelloController {

    @FXML
    public TextField tfPlayerName;

    private static PlayerDetails playerDetails;

    public void startGame() throws IOException {

        String playerName = tfPlayerName.getText();

        if (playerName == "") {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setHeaderText("Player name not inserted!");
            alert.setContentText("You must enter the player name to proceed...");
            alert.showAndWait();
        } else {
            playerDetails = new PlayerDetails(playerName);
            System.out.println(playerDetails.getPlayerName());
            sceneSwitcher("game-view.fxml", 930, 580);
        }
    }


    public static void sceneSwitcher(String name, int v, int v1) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(name));
        Scene scene = new Scene(fxmlLoader.load(), v, v1);
        HelloApplication.getMainStage().setTitle("Snake game!");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    public void exitApplication() throws IOException {
        HelloApplication.getMainStage().close();
    }

    public static PlayerDetails getPlayerDetails() {
        return playerDetails;
    }

}