package thiar.dah.multiclientchat.model;

import thiar.dah.multiclientchat.controller.ClientController;

public class ThreadReceiveClient implements Runnable {
    private ClientController cliente;

    public ThreadReceiveClient(ClientController cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        while (true) {
            // Leer del flujo y escribir en el chat
        }
    }
}
