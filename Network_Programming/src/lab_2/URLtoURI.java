package lab_2;

import java.net.URL;
import java.net.URI;

public class URLtoURI {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://www.creepypasta.com/");

            URI uri = url.toURI();
            System.out.println("Converted URI: " + uri);

            URL backToURL = uri.toURL();
            System.out.println("Back to URL: " + backToURL);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}