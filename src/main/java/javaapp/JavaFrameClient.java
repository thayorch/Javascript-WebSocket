package javaapp;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import javax.swing.*;
import java.awt.*;
import java.net.URI;

public class JavaFrameClient extends JFrame {
    private JTextField inputField;
    private JButton sendButton;
    private JLabel responseLabel;
    private WebSocketClient webSocketClient;

    public JavaFrameClient() {
        setTitle("WebSocket Java JFrame Client");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        inputField = new JTextField(20);
        sendButton = new JButton("Send to Server");
        responseLabel = new JLabel("Response will appear here");

        add(inputField);
        add(sendButton);
        add(responseLabel);

        sendButton.addActionListener(e -> {
            String message = inputField.getText();
            if (webSocketClient != null && webSocketClient.isOpen()) {
                webSocketClient.send(message);
            } else {
                responseLabel.setText("WebSocket not connected.");
            }
        });

        connectToServer();
    }

    private void connectToServer() {
        try {
            URI serverURI = new URI("ws://localhost:3000");
            webSocketClient = new WebSocketClient(serverURI) {
                @Override
                public void onOpen(ServerHandshake handshake) {
                    System.out.println("Connected to WebSocket server");
                    responseLabel.setText("Connected to server!");
                }

                @Override
                public void onMessage(String message) {
                    responseLabel.setText("Server: " + message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("Connection closed: " + reason);
                    responseLabel.setText("Disconnected from server");
                }

                @Override
                public void onError(Exception ex) {
                    ex.printStackTrace();
                    responseLabel.setText("Error occurred");
                }
            };

            webSocketClient.connect();
        } catch (Exception e) {
            e.printStackTrace();
            responseLabel.setText("Error connecting to server");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JavaFrameClient frame = new JavaFrameClient();
            frame.setVisible(true);
        });
    }
}
