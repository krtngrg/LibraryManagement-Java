package lab_6;

import java.io.*;
import java.net.*;

public class SimpleHttpServer {
    public static void main(String[] args) {
        int port = 8080;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started at http://localhost:" + port);
            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                    String line;
                    while (!(line = in.readLine()).isEmpty()) {
                        System.out.println(line);
                    }
                    
                    String response = "HTTP/1.1 200 OK\r\n" +
                            "Content-Type: text/html\r\n" +
                            "\r\n" +
                            "<html>\n" +
                            "  <body>\n" +
                            "    <h1>Hello from Simple HTTP Server</h1>\n" +
                            "  </body>\n" +
                            "</html>\n";

                    out.write(response);
                    out.flush();
                } catch (IOException e) {
                    System.out.println("Client connection error: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}