package multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastSender {

    public static void main(String[] args) {
        String message = "SWSC BCA student 6th Semester";
        String multicastAddress = "224.0.0.1";
        int port = 8888;

        try {
            MulticastSocket socket = new MulticastSocket();
            socket.setTimeToLive(1);
            byte[] buffer = message.getBytes();

            InetAddress group = InetAddress.getByName(multicastAddress);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);

            socket.send(packet);
            System.out.println("Message sent to multicast group: " + message);
            
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}