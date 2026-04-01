package lab_1;

import java.net.*;

public class IPVersionChecker {

    public static void main(String[] args) {

        try {
            
            InetAddress inet = InetAddress.getByName("www.youtube.com");
            System.out.println("Host Name: " + inet.getHostName());
            System.out.println("IP Address: " + inet.getHostAddress());

            if (inet instanceof Inet4Address) {
                System.out.println("This server is using IPv4.");
            }
            else if (inet instanceof Inet6Address) {
                System.out.println("This server is using IPv6.");
            }
            else {
                System.out.println("Unknown IP version.");
            }

        } catch (UnknownHostException e) {
            System.out.println("Invalid host: " + e.getMessage());
        }
    }
}