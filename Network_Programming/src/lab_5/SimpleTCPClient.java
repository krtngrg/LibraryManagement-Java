package lab_5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;

public class SimpleTCPClient extends JFrame {

    private JTextField hostField;
    private JTextField portField;
    private JTextField messageField;
    private JTextArea chatArea;
    private JButton connectButton;
    private JButton sendButton;
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public SimpleTCPClient() {
        setTitle("Simple TCP Client");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        hostField = new JTextField("localhost", 15);
        portField = new JTextField("12345", 5);
        connectButton = new JButton("Connect");

        topPanel.add(new JLabel("Host:"));
        topPanel.add(hostField);
        topPanel.add(new JLabel("Port:"));
        topPanel.add(portField);
        topPanel.add(connectButton);

        add(topPanel, BorderLayout.NORTH);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        messageField = new JTextField();
        sendButton = new JButton("Send");
        sendButton.setEnabled(false);
        bottomPanel.add(messageField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        connectButton.addActionListener(e -> connectToServer());
        sendButton.addActionListener(e -> sendMessage());
        messageField.addActionListener(e -> sendMessage());
    }

    private void connectToServer() {
        String host = hostField.getText().trim();
        int port;
        try {
            port = Integer.parseInt(portField.getText().trim());
        } catch (NumberFormatException ex) {
            chatArea.append("Invalid port number.\n");
            return;
        }
        try {
            socket = new Socket(host, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            chatArea.append("Connected to " + host + ":" + port + "\n");
            sendButton.setEnabled(true);
            new Thread(() -> {
                String line;
                try {
                    while ((line = reader.readLine()) != null) {
                        chatArea.append("Server: " + line + "\n");
                    }
                } catch (IOException ex) {
                    chatArea.append("Connection closed.\n");
                }
            }).start();

        } catch (IOException ex) {
            chatArea.append("Error connecting to server: " + ex.getMessage() + "\n");
        }
    }

    private void sendMessage() {
        String message = messageField.getText().trim();
        if (message.isEmpty() || writer == null) return;

        try {
            writer.write(message + "\n");
            writer.flush();
            chatArea.append("You: " + message + "\n");
            messageField.setText("");
        } catch (IOException ex) {
            chatArea.append("Error sending message.\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimpleTCPClient client = new SimpleTCPClient();
            client.setVisible(true);
        });
    }
}
