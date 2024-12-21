package thiar.dah.multiclientchat.server.controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import thiar.dah.multiclientchat.server.model.LaunchServer;

import java.io.IOException;

public class ServerController extends Application {
    @FXML
    private ListView<String> chatPane; // Para mostrar mensajes del chat

    @FXML
    private Label onlineCountLabel; // Etiqueta que muestra el número de usuarios en línea

    @FXML
    private ListView<String> userList; // Para mostrar la lista de usuarios conectados

    public static void main(String[] args) {
        launch(args); // Lanzar la aplicación JavaFX
    }

    public ServerController() {
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Cargar el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/thiar/dah/multiclientchat/server/server.fxml"));
            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Chat Server");
            primaryStage.show();

            // Obtener el controlador y pasar la referencia a LaunchServer
            LaunchServer launchServer = new LaunchServer(this);
            new Thread(launchServer).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void closeApplication(MouseEvent event) {
        System.exit(0); // Cerrar la aplicación
    }

    public void writeText(String text) {
        chatPane.getItems().add(text); // Añadir texto al chatPane
    }

    public void updateOnlineCount(int count) {
        onlineCountLabel.setText("Usuarios en línea: " + count); // Actualizar el conteo
    }

    public ListView<String> getChatPane() {
        return chatPane;
    }

    public ListView<String> getUserList() {
        return userList;
    }
}
