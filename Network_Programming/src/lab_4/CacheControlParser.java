package lab_4;

import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class CacheControlParser {

    public static void main(String[] args) {
        try {

            URL url = new URL("https://httpbin.org/cache/60");
            URLConnection connection = url.openConnection();

            connection.getInputStream().close();

            String cacheControl = connection.getHeaderField("Cache-Control");
            if (cacheControl == null) {
                System.out.println("No Cache-Control header found.");
                return;
            }

            System.out.println("Raw Header: " + cacheControl);
            String[] directives = cacheControl.split(",");

            boolean isPublic = false;
            boolean isPrivate = false;
            boolean noCache = false;
            int maxAge = -1;

            for (String directive : directives) {
                directive = directive.trim();

                if (directive.equalsIgnoreCase("public")) {
                    isPublic = true;
                } else if (directive.equalsIgnoreCase("private")) {
                    isPrivate = true;
                } else if (directive.equalsIgnoreCase("no-cache")) {
                    noCache = true;
                } else if (directive.startsWith("max-age=")) {
                    String value = directive.substring(8);
                    maxAge = Integer.parseInt(value);
                }
            }

            System.out.println("\nParsed Information:");
            System.out.println("Public: " + isPublic);
            System.out.println("Private: " + isPrivate);
            System.out.println("No-Cache: " + noCache);
            System.out.println("Max-Age: " + maxAge + " seconds");
            
            if (maxAge > 0) {
                Date now = new Date();
                Date expiry = new Date(now.getTime() + (maxAge * 1000L));

                System.out.println("Current Time: " + now);
                System.out.println("Expiry Time: " + expiry);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}