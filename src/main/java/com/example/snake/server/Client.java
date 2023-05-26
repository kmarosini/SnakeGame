package com.example.snake.server;

import com.example.snake.models.Direction;
import com.example.snake.models.SerializableSnake;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try(Socket clientSocket = new Socket(Server.HOST, Server.PORT)) {

            process(clientSocket);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void process(Socket clientSocket) throws IOException, ClassNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());


        oos.writeObject(new SerializableSnake(5.0,6.0,5, Direction.UP, "2"));

        System.out.println("In client: " + ois.readObject());
    }
}
