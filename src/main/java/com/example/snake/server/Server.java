package com.example.snake.server;

import com.example.snake.models.SerializableSnake;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int PORT = 1919;
    public static final String HOST = "localhost";

    public static void main(String[] args) {
        accept();
    }

    private static void accept() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();

                new Thread(() -> process(clientSocket)).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void process(Socket clientSocket) {
        try(ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream())) {

            SerializableSnake snake = (SerializableSnake) ois.readObject();

            System.out.println(snake);


            oos.writeObject(snake);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int Vowles(String message) {
        return  message.toLowerCase().replaceAll("[^aeiou]", "").length();
    }
}
