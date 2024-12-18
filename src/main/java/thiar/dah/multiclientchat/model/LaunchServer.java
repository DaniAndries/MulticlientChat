package thiar.dah.multiclientchat.model;

import thiar.dah.multiclientchat.controller.ServerController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class LaunchServer implements Runnable {
    final public ServerController serverInterface;

    public LaunchServer(ServerController serverInterface) {
        this.serverInterface = serverInterface;
    }

    public void sendMsg(String msg) {

    }

    @Override
    public void run() {
        ServerSocket serverSocket;
        final int PORT = 9876;
        try {
            ArrayList<ConectionThread> connections = new ArrayList<>();
            serverSocket = new ServerSocket(PORT);
            serverInterface.writeText("### Incised server");
            while (true) {
                Socket connection;
                connection = serverSocket.accept();
                serverInterface.writeText("--- Connection request received");
                ConectionThread ct = new ConectionThread(this, connection);
                connections.add(ct);
                new Thread(ct).start();
            }
        } catch (IOException e) {
            System.out.println("An error has occurred and the server has not started.");
            return;
        }
    }
}
