module com.example.snake {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires java.rmi;
    requires java.naming;


    opens com.example.snake to javafx.fxml;
    exports com.example.snake;
    exports com.example.snake.rmiserver to java.rmi;
}