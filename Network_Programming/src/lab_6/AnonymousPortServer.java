package lab_6;

import java.io.IOException;
import java.net.ServerSocket;

public class AnonymousPortServer {

    public static void main(String[] args) {
        try {
            
            ServerSocket serverSocket = new ServerSocket(0);
            
            int assignedPort = serverSocket.getLocalPort();
            System.out.println("ServerSocket created on anonymous port: " + assignedPort);

            System.out.println("Server is listening... Press Ctrl+C to stop.");
            while (true) {
                serverSocket.accept(); 
            }

        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}