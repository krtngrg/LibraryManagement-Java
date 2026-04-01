package lab_1;

import java.net.*;

public class InetTest {

    public static void main(String[] args) {

        try {
            InetAddress address = InetAddress.getByName("www.google.com");

            System.out.println("Host Name: " + address.getHostName());
            System.out.println("IP Address: " + address.getHostAddress());
            System.out.println("------------ Address Nature ------------");

            
            System.out.println("Is Any Local Address? " + address.isAnyLocalAddress());
            
            System.out.println("Is Link Local Address? " + address.isLinkLocalAddress());

            System.out.println("Is Loopback Address? " + address.isLoopbackAddress());

            System.out.println("Is Multicast Address? " + address.isMulticastAddress());
            
            System.out.println("Is Multicast Global? " + address.isMCGlobal());
            
            System.out.println("Is Multicast Link Local? " + address.isMCLinkLocal());

            System.out.println("Is Multicast Node Local? " + address.isMCNodeLocal());
            
            System.out.println("Is Multicast Organization Local? " + address.isMCOrgLocal());
            
            System.out.println("Is Multicast Site Local? " + address.isMCSiteLocal());

            System.out.println("Is Site Local Address? " + address.isSiteLocalAddress());

        } catch (UnknownHostException e) {
            System.out.println("Invalid host or IP address: " + e.getMessage());
        }
    }
}