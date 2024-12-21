package thiar.dah.multiclientchat.server.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConectionThread extends Thread { // Cambiar Runnable a Thread
    private Socket conection;
    private LaunchServer server;
    private DataInputStream inputFlow;
    private DataOutputStream outputFlow;
    private String username; // Almacena el nombre de usuario

    public ConectionThread(LaunchServer server, Socket conexion) {
        this.conection = conexion;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            inputFlow = new DataInputStream(conection.getInputStream());
            outputFlow = new DataOutputStream(conection.getOutputStream());

            // Escuchar el primer mensaje para obtener el nombre de usuario
            String firstMessage = inputFlow.readUTF();
            if (firstMessage.startsWith("CON ")) {
                username = firstMessage.substring(4); // Obtener nombre de usuario
                server.serverController.writeText(username + " se ha conectado."); // Mensaje en el servidor
                server.addClient(this); // Añadir al servidor
            }

            while (true) {
                String lectura = inputFlow.readUTF();
                String comando = lectura.substring(0, 3);
                switch (comando) {
                    case "MSG":
                        server.sendMsg(lectura.substring(4));
                        break;
                    case "PRV":
                        String[] parts = lectura.split(" ", 3); // Dividir el mensaje en partes
                        if (parts.length == 3) {
                            String recipient = parts[1]; // Destinatario
                            String privateMessage = parts[2]; // Mensaje
                            server.sendPrivateMessage(recipient, privateMessage, outputFlow);
                        }
                        break;
                    case "LUS":
                        // Enviar lista de usuarios
                        String users = String.join(", ", server.getConnectedUsers());
                        outputFlow.writeUTF("LUS " + users);
                        break;
                    case "EXI":
                        return; // Salir del bucle si el cliente se desconecta
                    default:
                        throw new IllegalArgumentException("Comando incorrecto");
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo crear algún flujo");
        } finally {
            try {
                inputFlow.close();
                outputFlow.close();
                conection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            server.removeClient(this); // Remover cliente
            server.serverController.writeText(username + " se ha desconectado."); // Mensaje en el servidor
        }
    }

    public String getUsername() {
        return username; // Método para obtener el nombre de usuario
    }

    public DataOutputStream getOutputStream() {
        return outputFlow; // Método para obtener el flujo de salida
    }
}
