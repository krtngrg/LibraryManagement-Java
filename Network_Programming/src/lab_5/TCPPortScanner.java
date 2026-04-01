package lab_5;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class TCPPortScanner {

    public static void main(String[] args) {

        try {
            InetAddress address = InetAddress.getByName("localhost");
            System.out.println("Scanning host: " + address.getHostAddress());

            for (int port = 1024; port <= 65535; port++) {
                try (Socket socket = new Socket()) {
                    SocketAddress socketAddress =
                            new InetSocketAddress(address, port);
                    socket.connect(socketAddress, 200);
                    System.out.println("Port " + port + " is OPEN.");
                } catch (IOException e) {
                    
                }
            }

        } catch (UnknownHostException e) {
            System.out.println("Host not found.");
        }

        System.out.println("Scan complete.");
    }
}