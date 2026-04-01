package lab_1;

import java.net.*;
import java.util.*;

public class NetworkInterfaceDemo {

    public static void main(String[] args) {
        try {

            Enumeration<NetworkInterface> interfaces =
                    NetworkInterface.getNetworkInterfaces();

            System.out.println("---- Network Interfaces ----");

            while (interfaces.hasMoreElements()) {

                NetworkInterface netInt = interfaces.nextElement();

                System.out.println("\nInterface Name: " + netInt.getName());
                System.out.println("Display Name: " + netInt.getDisplayName());

                System.out.println("Is Up? " + netInt.isUp());
                System.out.println("Is Loopback? " + netInt.isLoopback());
                System.out.println("Supports Multicast? " + netInt.supportsMulticast());

                
                byte[] mac = netInt.getHardwareAddress();
                if (mac != null) {
                    StringBuilder macAddress = new StringBuilder();
                    for (int i = 0; i < mac.length; i++) {
                        macAddress.append(String.format("%02X%s",
                                mac[i], (i < mac.length - 1) ? "-" : ""));
                    }
                    System.out.println("MAC Address: " + macAddress);
                } else {
                    System.out.println("MAC Address: Not Available");
                }

                Enumeration<InetAddress> inetAddresses = netInt.getInetAddresses();
                System.out.println("Associated IP Addresses:");
                while (inetAddresses.hasMoreElements()) {
                    InetAddress addr = inetAddresses.nextElement();
                    System.out.println("  - " + addr.getHostAddress());
                }
            }

        } catch (SocketException e) {
            System.out.println("Error retrieving network interface information: " + e.getMessage());
        }
    }
}