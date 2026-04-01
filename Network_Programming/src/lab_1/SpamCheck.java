package lab_1;

import java.net.*;
import java.util.Scanner;

public class SpamCheck {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter IPv4 address to check: ");
        String ip = scanner.nextLine().trim();

        try {
            
            String[] parts = ip.split("\\.");
            if (parts.length != 4) {
                System.out.println("Invalid IPv4 format.");
                return;
            }

            
            String reversedIP = parts[3] + "." + parts[2] + "." +
                    parts[1] + "." + parts[0];

            
            String query = reversedIP + ".zen.spamhaus.org";

            System.out.println("Querying: " + query);

            try {
                InetAddress result = InetAddress.getByName(query);
                System.out.println("Result IP: " + result.getHostAddress());
                System.out.println("Status: BLACKLISTED (Listed in Spamhaus)");

            } catch (UnknownHostException e) {
                System.out.println("Status: NOT Blacklisted (Clean)");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }
}