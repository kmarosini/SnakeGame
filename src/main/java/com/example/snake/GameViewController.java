package com.example.snake;

import com.example.snake.models.*;
import com.example.snake.utils.ReflectionUtils;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class GameViewController implements Initializable {

    public static Food food = new Food();

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    Image image = new Image("simple-apple.png");


    private static int speed;
    private static int width;
    private static int height;
    private static int cornerSize;

    private static boolean gameOver = false;
    private int snakeSizeCounter = 0;

    private int snakeScore;

    long lastTick = 0;


    private static List<Position> snake = new ArrayList<>();
    private static Position startPosition = new Position();
    private static Random rand = new Random();
    private SnakeLenght size = new SnakeLenght();

    @FXML
    private Canvas cnSnakeBoard;

    @FXML
    private Label lblLoad;

    @FXML
    private AnchorPane apGameWindow;

    @FXML
    private Label lblPlayerScore;

    @FXML
    private Button playButton;

    @FXML
    private Button restartButton;

    @FXML
    private Label lblDocumentation;

    @FXML
    private Label lblSave;

    @FXML
    private Label lblGameOver;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void btnPlayButtonClick() {
        restartGame();
    }

    private void restartGame() {

        lblGameOver.setText("");
        gameOver = false;
        speed = 3;
        lastTick = 0;
        playButton.setText("Restart");
        startGame(null, 0);
        playButton.setDisable(true);
    }

    @FXML
    private void lblDocumentationClick() {
        ReflectionUtils.generateDocumentation();
    }

    private void startGame(List<Position> snakePosition, int score) {
        speed = 3;
        width = 20;
        height = 20;
        cornerSize = 25;

        if (snakePosition == null && score == 0) {
            snake = new ArrayList<>();

            startPosition.setX(5);
            startPosition.setY(19);

            snakeScore = 0;
            lblPlayerScore.setText("0");

            size.setDirection(Direction.UP);
            size.setSnakeSize(2);
        } else {
             // TODO: Popraviti snakesize prilikom loadanja nazad, popraviti score counter prilikom loadanja nazad
            //  ovdje je probem, snake size je 4 a trebao bi biti 2
            snakeScore = Integer.parseInt(String.valueOf(score));
            snake = snakePosition;
        }



        // crtanje po Canvas elementu
        GraphicsContext gc = cnSnakeBoard.getGraphicsContext2D();



        // Food
        createFood();

         new AnimationTimer() {
            @Override
            public void stop() {
                if (gameOver) {
                    super.stop();
                }
            }

            @Override
            public void handle(long now) {
                if (lastTick == 0) {
                    lastTick = now;
                    tick(gc);
                    return;
                }

                if (now - lastTick > 1000000000 / speed) {
                    lastTick = now;
                    tick(gc);
                }
            }
        }.start();


        // control
        apGameWindow.requestFocus();

        apGameWindow.setOnKeyPressed(event -> {
            System.out.println("USO");
            if (event.getCode() == KeyCode.W){
                System.out.println("STISO SI GORE");
                size.setDirection(Direction.UP);
            }
            if (event.getCode() == KeyCode.A){
                size.setDirection(Direction.LEFT);
            }
            if (event.getCode() == KeyCode.S){
                size.setDirection(Direction.DOWN);
            }
            if (event.getCode() == KeyCode.D){
                size.setDirection(Direction.RIGHT);
            }
        });
        // Adding snake parts
        for (int i = 0; i < size.getSnakeSize(); i++) {
            snake.add(new Position(startPosition.getX(), startPosition.getY()));
        }
    }

    private void tick(GraphicsContext gc) {
        if (gameOver) {
            lblGameOver.setText("GAME OVER");
            playButton.setDisable(false);
            return;
        }

        lblGameOver.setText("");
        if (snakeScore == 0) {
            lblPlayerScore.setText(String.valueOf(size.getSnakeSize() - 2));
        } else {
            lblPlayerScore.setText(String.valueOf(Integer.parseInt(String.valueOf(snakeScore))));
        }
        //lblPlayerScore.setText(String.valueOf(size.getSnakeSize() - 2));

        // Get size
        size.snakeLenght(snake);
        startPosition.setX(snake.get(0).getX());
        startPosition.setY(snake.get(0).getY());

        switch (size.getDirection()) {
            case UP -> {
                snake.get(0).setY(snake.get(0).getY() - 1);
                if (snake.get(0).getY() < 0) {
                    gameOver = true;
                }
            }
            case DOWN -> {
                snake.get(0).setY(snake.get(0).getY() + 1);
                //postavljanje granica za zid
                if (snake.get(0).getY() >= height) {
                    gameOver = true;
                }
            }
            case LEFT -> {
                snake.get(0).setX(snake.get(0).getX() - 1);
                if (snake.get(0).getX() < 0) {
                    gameOver = true;
                }
            }
            case RIGHT -> {
                snake.get(0).setX(snake.get(0).getX() + 1);
                if (snake.get(0).getX() >= width) {
                    gameOver = true;
                }
            }
        }

        // Eating food
        if (food.getfX() == snake.get(0).getX() && food.getfY() == snake.get(0).getY()) {
            snake.add(new Position(-1, -1));
            size.setSnakeSize(size.getSnakeSize() + 1);
            snakeSizeCounter++;

            if (snakeScore != 0) {
                snakeScore++;
            }

            createFood();
        }

        // Self destroy
        for (int i = 1; i < snake.size(); i++){
            if (snake.get(0).getX() == snake.get(i).getX() && snake.get(0).getY() == snake.get(i).getY()) {
                gameOver = true;
            }
        }


        gc.setFill(Color.WHITE);
        gc.fillRect(0,0,width * cornerSize, height * cornerSize);

        gc.setFill(new ImagePattern(image));
        System.out.println(food.getfX());
        System.out.println(food.getfY());
        gc.fillOval(food.getfX() * cornerSize, food.getfY() * cornerSize, cornerSize, cornerSize);

        // snake color

        for (Position p : snake) {
            gc.setFill(Color.PINK);
            gc.fillRect(p.getX() * cornerSize, p.getY() * cornerSize, cornerSize - 1, cornerSize - 1);
            gc.setFill(Color.BLUEVIOLET);
            gc.fillRect(p.getX() * cornerSize, p.getY() * cornerSize, cornerSize - 2, cornerSize - 2);
        }
    }

    private void createFood() {
        while (true) {
            food.setfX(rand.nextInt(width));
            food.setfY(rand.nextInt(height));
            food.setfColor(rand.nextInt(5));
            speed++;
            break;
        }
    }

    public void saveGame() throws IOException {

        List<SerializableSnake> data = new ArrayList<>();

        snakeScore = Integer.parseInt(lblPlayerScore.getText());

        if (!gameOver) {
            for (int i = 0; i < size.getSnakeSize(); i++) {
                data.add(new SerializableSnake(snake.get(i).getX(),snake.get(i).getY(),size.getSnakeSize(), size.getDirection(), lblPlayerScore.getText()));
            }

            try(ObjectOutputStream serialize = new ObjectOutputStream(new FileOutputStream("saveGame.ser"))) {
                serialize.writeObject(data);
            }

        } else {
            System.out.println("The game is over! You can't save.");
        }
    }

    public void loadGame() throws IOException, ClassNotFoundException {

        List<Position> snakeList = new ArrayList<>();

        if (gameOver) {
            for (int i = 0; i < size.getSnakeSize(); i++) {
                snake.add(new Position(startPosition.getX(), startPosition.getY()));
            }

            try(ObjectInputStream deserialize = new ObjectInputStream(new FileInputStream("saveGame.ser"))) {
                List<SerializableSnake> data = (List<SerializableSnake>) deserialize.readObject();

                for (SerializableSnake snake: data) {
                    snakeList.add(new Position((int) snake.getPositionX(), (int) snake.getPositionY()));
                }

                // zadrzavanje pozicije u trenutku klika na saveGame
                data.forEach(s -> size.setDirection(s.getDirection()));

                // spremanje trenutnog rezultata
                //data.forEach(s -> lblPlayerScore.setText(String.valueOf(s.getSnakeSize() - 2)));
                lblPlayerScore.setText(String.valueOf(data.get(0).getScore()));

                snakeScore = Integer.parseInt(lblPlayerScore.getText());

                size.snakeLenght(snakeList);
                lblGameOver.setText(String.valueOf(snakeScore));
                gameOver = false;
                startGame(snakeList, snakeScore);
            }
        } else {
            System.out.println("You can't load the game yet!");
        }

    }
}
