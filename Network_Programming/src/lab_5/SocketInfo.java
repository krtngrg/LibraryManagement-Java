package lab_5;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.SocketException;

public class SocketInfo {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("www.swsc.edu.np", 80);

            System.out.println("Remote Host: " + socket.getInetAddress());
            System.out.println("Remote Port: " + socket.getPort());
            System.out.println("Local Address: " + socket.getLocalAddress());
            System.out.println("Local Port: " + socket.getLocalPort());

            socket.close();

        } catch (UnknownHostException e) {
            System.err.println("Host could not be resolved.");
        } catch (SocketException e) {
            System.err.println("Connection failed (Socket error).");
        } catch (IOException e) {
            System.err.println("I/O error occurred.");
        }
    }
}
