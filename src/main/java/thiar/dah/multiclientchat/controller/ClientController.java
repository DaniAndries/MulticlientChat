package thiar.dah.multiclientchat.controller;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import static javafx.application.Application.launch;


public class ClientController {
    private Stage frmClienteChat;
    private TextField campoNick;
    private TextField campoTexto;
    private Socket conexion;
    private InetSocketAddress direccion;
    private BufferedReader flujoEntrada;
    private PrintWriter flujoSalida;
    private WebView panelInfo;


    /**
     * Create the application.
     */
    public ClientController() {
        initialize();
        connect();
        recibir();
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        launch(ClientController.class, args);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmClienteChat.setTitle("Cliente Chat");

        BorderPane root = new BorderPane();

        // HTML Display Area
        panelInfo = new WebView();
        panelInfo.getEngine().loadContent(""); // Set initial content if needed
        root.setCenter(panelInfo);

        // Top panel with Nick and Texto inputs
        VBox topPanel = new VBox(5);
        topPanel.setSpacing(5);

        // First Row: Nick
        HBox nickPanel = new HBox(5);
        Label nickLabel = new Label("Nick");
        campoNick = new TextField();
        campoNick.setEditable(false);
        campoNick.setText("");
        nickPanel.getChildren().addAll(nickLabel, campoNick);

        // Second Row: Texto
        HBox textoPanel = new HBox(5);
        Label textoLabel = new Label("Texto");
        campoTexto = new TextField();
        Button botonEnviar = new Button("Enviar");
        botonEnviar.setOnAction(e -> send());
        textoPanel.getChildren().addAll(textoLabel, campoTexto, botonEnviar);

        topPanel.getChildren().addAll(nickPanel, textoPanel);
        root.setTop(topPanel);

        // Scene and Stage setup
        Scene scene = new Scene(root, 450, 300);
        frmClienteChat.setScene(scene);
        frmClienteChat.show();
    }

    public void escribir(String s) {
        escribirColor(s, Color.BLACK);
    }

    private void escribirColor(String s, Color c) {
        Document doc = this.panelInfo.getDocument();
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
        try {
            doc.insertString(doc.getLength(), s + "\n", aset);
        } catch (BadLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void connect() {
        while (this.campoNick.getText().equals("")) {
            this.campoNick.setText(JOptionPane.showInputDialog(null, "Introduce tu apodo:"));
        }
        conexion = new Socket();
        direccion = new InetSocketAddress("localhost", 9876);
        try {
            conexion.connect(direccion);
            flujoEntrada = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            flujoSalida = new PrintWriter(conexion.getOutputStream());
            escribirColor("Conexión establecida", Color.BLUE);
            flujoSalida.println("CON " + this.campoNick.getText());
            flujoSalida.flush();
        } catch (IOException e) {
            System.out.println("Se ha producido algún error en la conexión");
            escribirColor("Se ha producido algún error en la conexión", Color.RED);
            return;
        }
    }


    private void recibir() {
        new Thread(new HiloRecibirCliente(this)).start();
    }

    public BufferedReader getEntrada() {
        return flujoEntrada;
    }
}
}
