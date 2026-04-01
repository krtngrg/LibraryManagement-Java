package lab_2;

import java.net.URI;

public class URISpecialChars {
    public static void main(String[] args) {
        try {
            URI u = new URI("https://example.com/über?city=Zürich");
            
            System.out.println("toString(): " + u.toString());
            System.out.println("toExternalForm(): " + u.toString()); 
            System.out.println("toASCIIString(): " + u.toASCIIString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}