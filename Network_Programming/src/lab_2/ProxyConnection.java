package lab_2;

import java.net.URL;
import java.net.URLConnection;

public class ProxyConnection {
    public static void main(String[] args) {
        try {
            System.setProperty("http.proxyHost", "127.0.0.1");
            System.setProperty("http.proxyPort", "3020");
            URL url = new URL("https://www.example.com");
            URLConnection connection = url.openConnection();
            connection.connect();
            System.out.println("Connection successful through proxy!");

        } catch (Exception e) {
            System.out.println("Error connecting via proxy: " + e.getMessage());
        } finally {
            System.clearProperty("http.proxyHost");
            System.clearProperty("http.proxyPort");
        }
    }
}