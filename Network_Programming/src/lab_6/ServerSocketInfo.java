package lab_6;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocketInfo {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(0);
            System.out.println("ServerSocket Info BEFORE closing:");
            System.out.println("Local Address: " + serverSocket.getInetAddress());
            System.out.println("Local Port: " + serverSocket.getLocalPort());
            System.out.println("Is Bound: " + serverSocket.isBound());
            System.out.println("Is Closed: " + serverSocket.isClosed());

            serverSocket.close();

            System.out.println("\nServerSocket Info AFTER closing:");
            System.out.println("Local Address: " + serverSocket.getInetAddress());
            System.out.println("Local Port: " + serverSocket.getLocalPort());
            System.out.println("Is Bound: " + serverSocket.isBound());
            System.out.println("Is Closed: " + serverSocket.isClosed());
        } catch (IOException e) {
            System.err.println("ServerSocket error: " + e.getMessage());
        }
    }
}