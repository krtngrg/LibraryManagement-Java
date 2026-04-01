package lab_2;

import java.net.URL;

public class URLExample {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://admin@www.example.com:8080/student.html?id=788#top");

            System.out.println("Protocol: " + url.getProtocol());
            System.out.println("Host: " + url.getHost());
            System.out.println("Port: " + url.getPort());
            System.out.println("User Info: " + url.getUserInfo());
            System.out.println("Path: " + url.getPath());
            System.out.println("Query: " + url.getQuery());
            System.out.println("Fragment (Ref): " + url.getRef());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}