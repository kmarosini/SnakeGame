package com.example.snake.rmiserver;

import com.example.snake.utils.JNDIHelper;

import javax.naming.NamingException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiServer {
    //private static final int RANDOM_PORT_HINT = 0;
    //private static final int RMI_PORT = 1919;

    public static void main(String[] args) {
        try {

            String rmiPortString = JNDIHelper.getConfigurationParameter("rmi.port");
            String rmiObjectName = JNDIHelper.getConfigurationParameter("remote_object_name");
            String random_port_hint = JNDIHelper.getConfigurationParameter("random_port_hint");

            Registry registry = LocateRegistry.createRegistry(Integer.parseInt(rmiPortString));
            ChatService chatService = new ChatServiceImpl();
            ChatService skeleton = (ChatService) UnicastRemoteObject.exportObject(chatService, Integer.parseInt(random_port_hint));
            registry.rebind(rmiObjectName, skeleton);
            System.err.println("Object registered in RMI registry");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NamingException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}
