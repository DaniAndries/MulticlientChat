package thiar.dah.multiclientchat.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConectionThread implements Runnable {
    private Socket conection;
    private LaunchServer server;
    private DataInputStream inputFlow;
    private DataOutputStream outputFlow;

    public ConectionThread(LaunchServer server, Socket conexion) {
        this.conection = conexion;
        this.server = server;
    }

    @Override
    public void run() {
        try {

            inputFlow = new DataInputStream(conection.getInputStream());
            outputFlow = new DataOutputStream(conection.getOutputStream());
            label:
            while (true) {
                String lectura = inputFlow.readLine();
                String comando = lectura.substring(0, 3);
                switch (comando) {
                    case "MSG":
                        server.sendMsg(lectura.substring(4));
                        break;
                    case "CHT":
                        //*TO-DO
                        break;
                    case "PRV":
                        //*TO-DO
                        break;
                    case "LUS":
                        //*TO-DO
                        break;
                    case "CON":
                        server.serverInterface.writeText("Has connected " + lectura.substring(4));
                        break;
                    case "EXI":
                        break label;
                    default:
                        throw new IllegalArgumentException("Wrong command");
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo crear algún flujo");
            return;
        } finally {
            try {
                inputFlow.close();
                outputFlow.close();
                conection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
