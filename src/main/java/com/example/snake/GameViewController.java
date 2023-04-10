package com.example.snake;

import com.example.snake.models.*;
import com.example.snake.rmiserver.ChatService;
import com.example.snake.utils.JNDIHelper;
import com.example.snake.utils.ReflectionUtils;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.*;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.naming.NamingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


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
    private static List<Replay> replayList = new ArrayList<>();

    private static Position startPosition = new Position();
    private static Random rand = new Random();
    private SnakeSize size = new SnakeSize();


    @FXML
    private Canvas cnSnakeBoard;

    @FXML
    public TextField tfMessage;

    @FXML
    private Label lblLoad;

    @FXML
    private AnchorPane apGameWindow;

    @FXML
    private Label lblPlayerScore;

    @FXML Button btnSend;


    @FXML
    public TextArea taChat;


    @FXML
    private Button playButton;

    @FXML
    private Button restartButton;

    @FXML
    private Label lblDocumentation;

    @FXML
    private Label lblSave;

    @FXML
    private Label lblReplay;

    @FXML
    private Label lblGameOver;

    ChatService stub = null;
    PlayerDetails playerDetails = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            String rmiObjectName = JNDIHelper.getConfigurationParameter("remote_object_name");

            Registry registry = LocateRegistry.getRegistry("localhost", 1919);
            stub = (ChatService) registry.lookup(rmiObjectName);

        } catch (NotBoundException | NamingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void btnPlayButtonClick() {
        restartGame();
    }

    @FXML
    private void lblReplayClick() {
        XML_Load();
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

    @FXML
    private void btnSendClick() {
        try {
            stub.sendMessage(tfMessage.getText(), HelloController.getPlayerDetails().getPlayerName());
            StringBuilder sb = new StringBuilder();
            stub.getChatHistory().forEach(a->sb.append(a + "\n"));
            taChat.setText(sb.toString());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void startGame(List<Position> snakePosition, int score) {
        speed = 3;
        width = 20;
        height = 20;
        cornerSize = 25;

        System.out.println("LIST SIZE: " + replayList.size());

        if (snakePosition == null && score == 0) {
            snake = new ArrayList<>();

            startPosition.setX(5);
            startPosition.setY(19);

            snakeScore = 0;
            lblPlayerScore.setText("0");

            size.setDirection(Direction.UP);
            size.setSnakeLength(2);
        } else {
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
        if(snakePosition == null) {
            for (int i = 0; i < size.getSnakeLength(); i++) {
                snake.add(new Position(startPosition.getX(), startPosition.getY()));
            }
        }
    }

    private void tick(GraphicsContext gc) {
        if (gameOver) {
            lblGameOver.setText("GAME OVER");
            playButton.setDisable(false);
            XML_Save();
            return;
        }

        // kreiranje replay liste na svaki tick
        replayList.add(new Replay(startPosition.getX(), startPosition.getY(), food.getfX(), food.getfY(), lblPlayerScore.getText(), size.getDirection()));



        lblGameOver.setText("");
        if (snakeScore == 0) {
            lblPlayerScore.setText(String.valueOf(size.getSnakeLength() - 2));
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
            size.setSnakeLength(size.getSnakeLength() + 1);
            snakeSizeCounter++;

            if (snakeScore != 0) {
                snakeScore++;
            }

            System.out.println("SIZE: " + size.getSnakeLength());

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
            for (int i = 0; i < size.getSnakeLength(); i++) {
                data.add(new SerializableSnake(snake.get(i).getX(),snake.get(i).getY(),size.getSnakeLength(), size.getDirection(), lblPlayerScore.getText()));
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
            /*for (int i = 0; i < size.getSnakeSize(); i++) {
                snake.add(new Position(startPosition.getX(), startPosition.getY()));
            }*/

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

    private void XML_Load() {
        try {
            File snakeStream = new File("C:\\Users\\karlo\\OneDrive\\Radna površina\\Java2\\Snake\\Record.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document xmlDocument = db.parse(snakeStream);
            xmlDocument.getDocumentElement().normalize();
            System.out.println("Root element: " + xmlDocument.getDocumentElement().getNodeName());
            NodeList nodeList = xmlDocument.getElementsByTagName("Snake");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                System.out.println("Node name: " + node.getNodeName());
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;

                    for (int j = 0; j < replayList.size(); j++) {
                        System.out.println("---------------------------------------------------------------------");
                        System.out.println("Position X: " + eElement.getElementsByTagName("X").item(j).getTextContent());
                        System.out.println("Position Y: " + eElement.getElementsByTagName("Y").item(j).getTextContent());
                        System.out.println("Food position X: " + eElement.getElementsByTagName("fX").item(j).getTextContent());
                        System.out.println("Food position Y: " + eElement.getElementsByTagName("fY").item(j).getTextContent());
                        System.out.println("Score: " + eElement.getElementsByTagName("Score").item(j).getTextContent());
                        System.out.println("Direction: " + eElement.getElementsByTagName("Direction").item(j).getTextContent());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void XML_Save(){
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            Document xmlDocument = dbf.newDocumentBuilder().newDocument();

            Element rootElement = xmlDocument.createElement("Snake");
            xmlDocument.appendChild(rootElement);

            for (int i = 0; i < replayList.size(); i++) {


                Element snake_element = xmlDocument.createElement("Position");
                Element element_x = xmlDocument.createElement("X");
                Node node_x = xmlDocument.createTextNode(String.valueOf(replayList.get(i).getPositionX()));
                element_x.appendChild(node_x);
                snake_element.appendChild(element_x);
                rootElement.appendChild(snake_element);
                //-------------------------------------------------

                Element element_y = xmlDocument.createElement("Y");
                Node node_y = xmlDocument.createTextNode(String.valueOf(replayList.get(i).getPositionY()));
                element_y.appendChild(node_y);
                snake_element.appendChild(element_y);
                rootElement.appendChild(snake_element);
                //-------------------------------------------------

                Element food_element = xmlDocument.createElement("Food");
                Element food_x = xmlDocument.createElement("fX");
                Node node_food_x = xmlDocument.createTextNode(String.valueOf(replayList.get(i).getFoodX()));
                food_x.appendChild(node_food_x);
                food_element.appendChild(food_x);
                rootElement.appendChild(food_element);
                //---------------------------------------------------

                Element food_y = xmlDocument.createElement("fY");
                Node node_food_y = xmlDocument.createTextNode(String.valueOf(replayList.get(i).getFoodY()));
                food_y.appendChild(node_food_y);
                food_element.appendChild(food_y);
                rootElement.appendChild(food_element);
                //----------------------------------------------------

                Element score_element = xmlDocument.createElement("Score");
                Node node_score = xmlDocument.createTextNode(String.valueOf(replayList.get(i).getScore()));
                score_element.appendChild(node_score);
                rootElement.appendChild(score_element);
                //----------------------------------------------------

                Element direction_element = xmlDocument.createElement("Direction");
                Node node_direction = xmlDocument.createTextNode(String.valueOf(replayList.get(i).getDirection()));
                direction_element.appendChild(node_direction);
                rootElement.appendChild(direction_element);
                //----------------------------------------------------------


                Transformer transformer = TransformerFactory.newInstance().newTransformer();

                Source xmlSource = new DOMSource(xmlDocument);
                Result xmlResult = new StreamResult(new File("Record.xml"));

                transformer.transform(xmlSource, xmlResult);

            }


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information!");
            alert.setHeaderText("File RECORD.xml was created!");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
