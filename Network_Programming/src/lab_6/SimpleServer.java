package lab_6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server started. Listening on port 5000...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from: " + clientSocket.getInetAddress());
                clientSocket.close();
            }

        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}