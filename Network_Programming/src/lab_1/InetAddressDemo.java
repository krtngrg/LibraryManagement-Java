package lab_1;

import java.net.*;

public class InetAddressDemo {

    public static void main(String[] args) {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            System.out.println("Local Host:");
            System.out.println("Host Name: " + localHost.getHostName());
            System.out.println("IP Address: " + localHost.getHostAddress());
            System.out.println();

            InetAddress googleHost = InetAddress.getByName("www.google.com");
            System.out.println("Google Host:");
            System.out.println("Host Name: " + googleHost.getHostName());
            System.out.println("IP Address: " + googleHost.getHostAddress());
            System.out.println();

            InetAddress[] microsoftHosts = InetAddress.getAllByName("www.microsoft.com");
            System.out.println("Microsoft Hosts:");
            for (InetAddress address : microsoftHosts) {
                System.out.println("Host Name: " + address.getHostName());
                System.out.println("IP Address: " + address.getHostAddress());
                System.out.println();
            }

        } catch (UnknownHostException e) {
            System.out.println("Host could not be resolved: " + e.getMessage());
        }
    }
}