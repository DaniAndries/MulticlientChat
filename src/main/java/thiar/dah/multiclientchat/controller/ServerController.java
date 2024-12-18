package thiar.dah.multiclientchat.controller;

import thiar.dah.multiclientchat.model.LaunchServer;

import javax.swing.*;
import java.awt.*;

public class ServerController {
    private JFrame frame;
    private JTextArea info;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ServerController window = new ServerController();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void writeText(String text) {

    }

    /**
     * Create the application.
     */
    public void ServerController() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        new Thread(new LaunchServer(this)).start();
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        info = new JTextArea();
        frame.getContentPane().add(getInfo(), BorderLayout.CENTER);
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.NORTH);
        JLabel lblNewLabel = new JLabel("Servidor de chat");
        panel.add(lblNewLabel);
    }

    public JTextArea getInfo() {
        return info;
    }
}
