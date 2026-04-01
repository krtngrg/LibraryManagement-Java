package lab_6;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ThreadedDaytimeServer {

    public static void main(String[] args) {
        int port = 8848; 
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Threaded Daytime Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                
                Thread clientHandler = new Thread(new ClientHandler(clientSocket));
                clientHandler.start();
            }

        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    
    static class ClientHandler implements Runnable {

        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                String currentTime = new Date().toString();
                out.println(currentTime);
                System.out.println("Sent date/time to client: " + clientSocket.getInetAddress());

                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error handling client: " + e.getMessage());
            }
        }
    }
}
