package com.example.snake.rmiserver;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatService extends Remote {

    //String REMOTE_OBJECT_NAME = "com.example.snake.rmiserver";
    void sendMessage(String message, String user) throws RemoteException;
    void clearChatHistory() throws RemoteException;
    List<String> getChatHistory() throws RemoteException;
}
