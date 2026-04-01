package lab_3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;

public class CustomCookiePolicy{

    public static void main(String[] args) {
        try {

            CookiePolicy policy = new CookiePolicy() {
                @Override
                public boolean shouldAccept(URI uri, HttpCookie cookie) {
                    String domain = cookie.getDomain();

                    if (domain != null && domain.endsWith(".gov")) {
                        System.out.println("Blocked .gov cookie from: " + domain);
                        return false;   
                    }
                    return true;        
                }
            };

            CookieManager cookieManager = new CookieManager(null, policy);
            CookieHandler.setDefault(cookieManager);

            URL url1 = new URL("https://httpbin.org/cookies/set?site=test");
            HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
            conn1.getInputStream().close();

            
            URL url2 = new URL("https://www.nasa.gov/");
            HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
            conn2.getInputStream().close();
            
            CookieStore store = cookieManager.getCookieStore();
            List<HttpCookie> cookies = store.getCookies();

            System.out.println("\nStored Cookies After Policy Applied:\n");
            for (HttpCookie cookie : cookies) {
                System.out.println("Name: " + cookie.getName());
                System.out.println("Domain: " + cookie.getDomain());
                System.out.println("-----------------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
