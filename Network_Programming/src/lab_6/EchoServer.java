package lab_6;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    public static void main(String[] args) {
        int port = 8848; 

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Echo Server started on port " + port);

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress());

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String line;
            while ((line = in.readLine()) != null) {
                if (line.equalsIgnoreCase("bye")) {
                    System.out.println("Client sent 'bye'. Closing connection.");
                    break;
                }
                out.println("Server: " + line);
            }

            clientSocket.close();
            System.out.println("Connection closed.");

        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}