package lab_6;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class DaytimeServer {

    public static void main(String[] args) {
        int port = 13; 
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Daytime server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                String currentTime = new Date().toString();

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println(currentTime);

                System.out.println("Sent date/time to client: " + clientSocket.getInetAddress());

                clientSocket.close();
            }

        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}