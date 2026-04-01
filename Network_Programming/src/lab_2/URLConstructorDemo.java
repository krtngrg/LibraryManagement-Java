package lab_2;

import java.net.*;

public class URLConstructorDemo {

    public static void main(String[] args) {

        try {
            URL url1 = new URL("https://example.com/");
            System.out.println("URL 1: " + url1);

            URL url2 = new URL("http", "example.com", "/index.html#top");
            System.out.println("URL 2: " + url2);

            URL url3 = new URL("http", "example.com", 8080, "/index.html#top");
            System.out.println("URL 3: " + url3);
            
            URL baseURL = new URL("https://example.com/docs/");
            URL url4 = new URL(baseURL, "../help.html");
            System.out.println("URL 4 (Relative to Base): " + url4);

        } catch (MalformedURLException e) {
            System.out.println("Malformed URL detected: " + e.getMessage());
        }
    }
}
