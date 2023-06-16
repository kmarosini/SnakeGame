package com.example.snake.Threads;

import com.example.snake.rmiserver.ChatService;
import javafx.scene.control.TextArea;

import java.rmi.RemoteException;

public class ChatThread implements Runnable{
    private ChatService chatService;
    private TextArea chatHistory;
    public ChatThread(ChatService chatService, TextArea chatHistory) {
        this.chatService = chatService;
        this.chatHistory = chatHistory;
    }
    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(1000);
                chatHistory.clear();
                StringBuilder chatHistoryBuilder = new StringBuilder();
                chatService.getChatHistory().forEach(a -> chatHistoryBuilder.append(a + "\n"));
                chatHistory.setText(chatHistoryBuilder.toString());

            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
