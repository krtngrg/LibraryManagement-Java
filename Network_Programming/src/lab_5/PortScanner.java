package lab_5;

import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;

public class PortScanner {

    public static void main(String[] args) {

        String host = "localhost";
        System.out.println("Scanning ports 1 to 1024 on host: " + host + "\n");
        for (int port = 1; port <= 1024; port++) {
            try (Socket socket = new Socket(host, port)) {
                System.out.println("Port " + port + " is OPEN");
            }
            catch (UnknownHostException e) {
                System.out.println("Host not found: " + host);
                break;  
            }
            catch (IOException e) {
                
            }
        }

        System.out.println("\nScan completed.");
    }
}