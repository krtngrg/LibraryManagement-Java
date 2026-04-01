package lab_1;

import java.net.*;

public class Ping {

    public static void main(String[] args) {

        try {
            
            InetAddress inet = InetAddress.getByName("www.example.com");
            System.out.println("Testing reachability for: " + inet.getHostName());
            
            boolean status = inet.isReachable(3000);
            if (status) {
                System.out.println("Host is reachable");
            } else {
                System.out.println("Host is not reachable");
            }

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
}