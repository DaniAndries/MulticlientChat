package thiar.dah.multiclientchat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerMain extends Application {
    public static void main(String[] args) {
        // Lanzar la aplicación JavaFX
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            // Cargar el archivo FXML
            FXMLLoader fxmlLoader = new FXMLLoader(ServerMain.class.getResource("/thiar/dah/multiclientchat/server/server.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700, 700); // Tamaño inicial de la ventana
            stage.setTitle("Server View");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Manejar errores de carga del FXML
            System.err.println("Error al cargar el archivo FXML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
