package com.example.snake.server;

import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Reciever {
    public Reciever() throws IOException {
        DatagramSocket socket = new DatagramSocket(1919);
        System.out.println("Receiver is running at port: " + socket.getLocalPort());

        // RECEIVING PART
        byte[] buffer = new byte[1500]; // MTU = 1500, maximum data you can transfer in a single packet
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        socket.receive(packet); // retrieving sender's message
    }


    public static void main(String[] args) {
        try {
            new Reciever();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
