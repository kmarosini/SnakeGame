package com.example.snake.rmiserver;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ChatServiceImpl implements ChatService{
    private List<String> chatHistory;

    public ChatServiceImpl() {
        chatHistory = new ArrayList<>();
    }

    @Override
    public void sendMessage(String message, String user) {
        chatHistory.add(user + ": " + message);
    }

    @Override
    public void clearChatHistory() throws RemoteException {
        chatHistory.clear();
    }
    @Override
    public List<String> getChatHistory() {
        return chatHistory;
    }
}
