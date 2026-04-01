package lab_6;

import java.io.*;
import java.net.*;

public class HttpRedirector {
    public static void main(String[] args) {
        int port = 8080;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Redirector running at http://localhost:" + port);

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()));
                    BufferedWriter out = new BufferedWriter(
                            new OutputStreamWriter(clientSocket.getOutputStream()));

                    String line;
                    while ((line = in.readLine()) != null && !line.isEmpty()) {
                        System.out.println(line);
                    }

                    String response =
                            "HTTP/1.1 301 Moved Permanently\r\n" +
                                    "Location: http://www.example.com\r\n" +
                                    "\r\n";

                    out.write(response);
                    out.flush();

                } catch (IOException e) {
                    System.out.println("Client error: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}
