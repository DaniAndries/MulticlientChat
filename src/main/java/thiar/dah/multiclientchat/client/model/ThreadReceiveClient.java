package thiar.dah.multiclientchat.client.model;

import thiar.dah.multiclientchat.client.controller.ClientController;

import java.io.BufferedReader;
import java.io.IOException;

public class ThreadReceiveClient implements Runnable {
    private final ClientController client; // Inmutabilidad
    private final BufferedReader input; // BufferedReader ahora es parte del constructor

    public ThreadReceiveClient(ClientController client, BufferedReader input) {
        this.client = client;
        this.input = input; // Obtener BufferedReader desde el constructor
    }

    @Override
    public void run() {
        String message;

        try {
            // Leer del flujo y escribir en el chat
            while ((message = input.readLine()) != null) {
                // Mostrar el mensaje en el chat pane
                client.coloredWrite(message, javafx.scene.paint.Color.BLUE);
            }
        } catch (IOException e) {
            // Manejar excepción en caso de error de lectura
            client.coloredWrite("Error al recibir mensajes: " + e.getMessage(), javafx.scene.paint.Color.RED);
            e.printStackTrace();
        }
        // No cerramos el BufferedReader aquí para no interferir con ClientController
    }
}
