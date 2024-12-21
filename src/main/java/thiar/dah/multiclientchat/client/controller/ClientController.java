package thiar.dah.multiclientchat.client.controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class ClientController extends Application {

    private Socket connection;
    private BufferedReader flowInput;
    private PrintWriter flowOutput;

    @FXML
    private ListView<TextFlow> chatPane;

    @FXML
    private TextArea messageBox;

    @FXML
    private Label onlineCountLabel;

    @FXML
    private HBox onlineUsersHbox;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private ListView<TextFlow> userList;

    @FXML
    private Label usernameLabel;

    private static final String EMPTY_MESSAGE_ERROR = "No puedes enviar un mensaje vacío.";
    private static final String CONNECTION_ESTABLISHED = "Conexión establecida como ";
    private static final String DISCONNECT_MESSAGE = "Te has desconectado del servidor.";

    @FXML
    void closeApplication(MouseEvent event) {
        disconnect();
    }

    @FXML
    void sendMethod(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            sendMessage();
        }
    }

    public ClientController() {
        // No se necesita la inicialización aquí, se hará en el método start
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Cargar el archivo FXML y establecer el controlador
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/thiar/dah/multiclientchat/client/client.fxml")));
        primaryStage.setTitle("Chat Client");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        initialize();
        connect();
    }

    public static void main(String[] args) {
        Application.launch(ClientController.class, args);
    }

    private void initialize() {
        statusComboBox.getItems().addAll("Disponible", "Ocupado", "Ausente");
        statusComboBox.getSelectionModel().selectFirst();

        chatPane.setCellFactory(param -> new ListCell<TextFlow>() {
            @Override
            protected void updateItem(TextFlow item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setGraphic(item);
                }
            }
        });

        messageBox.setOnKeyPressed(this::sendMethod);
        usernameLabel.setText("");
        onlineCountLabel.setText("0 usuarios conectados");
    }

    @FXML
    void sendButtonAction(ActionEvent event) {
        if (messageBox.getText().startsWith("PRV")) sendPrvMessage();
        sendMessage();
    }

    private void sendMessage() {
        String message = messageBox.getText().trim();
        if (!message.isEmpty()) {
            if (flowOutput != null) {
                flowOutput.println("MSG "+message);
                flowOutput.flush();
                coloredWrite("Tú: " + message, Color.GREEN);
                messageBox.clear();
            } else {
                coloredWrite("No estás conectado al servidor.", Color.RED);
            }
        } else {
            coloredWrite(EMPTY_MESSAGE_ERROR, Color.RED);
        }
    }

    private void sendPrvMessage(){
        String message = messageBox.getText();
        if (!message.isEmpty()) {
            if (flowOutput != null) {
                flowOutput.println("MSG "+message);
                flowOutput.flush();
                coloredWrite("Tú: " + Arrays.stream(message.split(",")).toList().get(1), Color.GREEN);
                messageBox.clear();
            } else {
                coloredWrite("No estás conectado al servidor.", Color.RED);
            }
        } else {
            coloredWrite(EMPTY_MESSAGE_ERROR, Color.RED);
        }
    }

    public void coloredWrite(String s, Color c) {
        Platform.runLater(() -> {
            Text text = new Text(s + "\n");
            text.setFill(c);
            TextFlow textFlow = new TextFlow(text);
            chatPane.getItems().add(textFlow);
        });
    }

    private void connect() {
        InetSocketAddress address;

        // Usar TextInputDialog para solicitar el apodo del usuario
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Introduce tu apodo");
        dialog.setHeaderText(null);
        dialog.setContentText("Por favor, introduce tu apodo:");

        // Esperar la respuesta del usuario
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().trim().isEmpty()) {
            usernameLabel.setText(result.get().trim());
        } else {
            coloredWrite("El apodo no puede estar vacío. Inténtalo de nuevo.", Color.RED);
            return;
        }

        connection = new Socket();
        address = new InetSocketAddress("localhost", 6000);
        try {
            connection.connect(address);
            flowInput = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            flowOutput = new PrintWriter(connection.getOutputStream(), true);
            flowOutput.println("CON " + this.usernameLabel.getText());
            coloredWrite("Conexión establecida como " + this.usernameLabel.getText(), Color.BLUE);
            updateOnlineCount(1); // Actualizar el conteo al conectarse
            receive();
        } catch (IOException e) {
            coloredWrite("Error al conectar al servidor: " + e.getMessage(), Color.RED);
            e.printStackTrace();
        }
    }

    private void receive() {
        new Thread(() -> {
            try {
                String message;
                while ((message = flowInput.readLine()) != null) {
                    coloredWrite(message, Color.BLUE);
                    handleUserStatusUpdate(message); // Manejar la actualización del estado del usuario
                }
            } catch (IOException e) {
                coloredWrite("Se ha perdido la conexión con el servidor.", Color.RED);
                e.printStackTrace();
            } finally {
                disconnect();
            }
        }).start();
    }

    private void handleUserStatusUpdate(String message) {
        if (message.startsWith("CONNECTION")) {
            // Ejemplo de formato de mensaje: CONNECTION username
            String username = message.split(" ")[1];
            updateOnlineCount(1); // Incrementar el conteo al conectar
        } else if (message.startsWith("DISCONNECT")) {
            // Ejemplo de formato de mensaje: DISCONNECT username
            String username = message.split(" ")[1];
            updateOnlineCount(-1); // Decrementar el conteo al desconectar
        }
    }

    private void updateOnlineCount(int change) {
        Platform.runLater(() -> {
            String currentText = onlineCountLabel.getText();
            int currentCount = Integer.parseInt(currentText.split(" ")[0]);
            currentCount += change;
            onlineCountLabel.setText(currentCount + " usuarios conectados");
        });
    }

    private void disconnect() {
        try {
            if (flowOutput != null) {
                flowOutput.println("DISCONNECT " + usernameLabel.getText());
                flowOutput.flush();
            }
            if (flowInput != null) flowInput.close();
            if (flowOutput != null) flowOutput.close();
            if (connection != null && !connection.isClosed()) connection.close();
            coloredWrite(DISCONNECT_MESSAGE, Color.ORANGE);
            updateOnlineCount(-1); // Decrementar el conteo al desconectar
            System.exit(0);
        } catch (IOException e) {
            coloredWrite("Error al cerrar la conexión: " + e.getMessage(), Color.RED);
            e.printStackTrace();
        }
    }

    public BufferedReader getInput() {
        return flowInput;
    }
}
