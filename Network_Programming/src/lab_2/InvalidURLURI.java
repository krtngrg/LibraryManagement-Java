package lab_2;

import java.net.URL;
import java.net.URI;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class InvalidURLURI {
    public static void main(String[] args) {
        try {
            URL badUrl = new URL("http//missingcolon.com"); 
            System.out.println("URL created: " + badUrl);
        } catch (MalformedURLException e) {
            System.out.println("Caught MalformedURLException:");
            System.out.println("Error message: " + e.getMessage());
        }
        System.out.println("----------------------------------");

        try {
            URI badUri = new URI("https://exa mple.com"); 
            System.out.println("URI created: " + badUri);
        } catch (URISyntaxException e) {
            System.out.println("Caught URISyntaxException:");
            System.out.println("Error message: " + e.getMessage());

        }
    }
}