package lab_2;

import java.net.URI;

public class URIExample {
    public static void main(String[] args) {
        try {
            
            URI uri1 = new URI("http://www.example.org/student?id=90#today");
            URI uri2 = new URI("mailto:info@example.com");
            URI uri3 = new URI("urn:isbn:0-395-36341-1");

            printURIDetails(uri1);
            printURIDetails(uri2);
            printURIDetails(uri3);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printURIDetails(URI uri) {
        System.out.println("URI: " + uri);
        System.out.println("  Scheme: " + uri.getScheme());
        System.out.println("  User Info: " + uri.getUserInfo());
        System.out.println("  Host: " + uri.getHost());
        System.out.println("  Port: " + uri.getPort());
        System.out.println("  Path: " + uri.getPath());
        System.out.println("  Query: " + uri.getQuery());
        System.out.println("  Fragment: " + uri.getFragment());
        System.out.println("  Authority: " + uri.getAuthority());
        System.out.println("  isAbsolute(): " + uri.isAbsolute());
        System.out.println("  isOpaque(): " + uri.isOpaque());
        System.out.println("-------------------------------------");
    }
}