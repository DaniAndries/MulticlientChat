package thiar.dah.multiclientchat.server.model;

import thiar.dah.multiclientchat.server.controller.ServerController;

import java.io.*;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

public class LaunchServer implements Runnable {
    ServerController serverController;
    private Set<ConectionThread> clientThreads = new HashSet<>(); // Mantener instancias de ConectionThread

    public LaunchServer(ServerController serverController) {
        this.serverController = serverController;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(6000)) {
            System.out.println("Servidor iniciado en el puerto 6000");
            while (true) {
                new ConectionThread(this, serverSocket.accept()).start(); // Crear un nuevo hilo para el cliente
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void addClient(ConectionThread clientThread) {
        clientThreads.add(clientThread);
        updateOnlineCount();
    }

    public synchronized void removeClient(ConectionThread clientThread) {
        clientThreads.remove(clientThread);
        updateOnlineCount();
    }

    public synchronized void sendMsg(String message) {
        for (ConectionThread client : clientThreads) {
            try {
                client.getOutputStream().writeUTF("MSG " + message);
            } catch (IOException e) {
                System.out.println("Error al enviar mensaje a " + client.getUsername());
            }
        }
    }

    public synchronized void sendPrivateMessage(String recipient, String message, DataOutputStream senderOutput) {
        for (ConectionThread client : clientThreads) {
            if (client.getUsername().equals(recipient)) {
                try {
                    client.getOutputStream().writeUTF("PRV " + senderOutput + ": " + message);
                } catch (IOException e) {
                    System.out.println("Error al enviar mensaje privado a " + recipient);
                }
                return; // Salir después de encontrar al destinatario
            }
        }
    }

    public synchronized Set<String> getConnectedUsers() {
        Set<String> usernames = new HashSet<>();
        for (ConectionThread client : clientThreads) {
            usernames.add(client.getUsername());
        }
        return usernames;
    }

    private void updateOnlineCount() {
        serverController.updateOnlineCount(clientThreads.size());
    }
}
