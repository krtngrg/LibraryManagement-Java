package lab_3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;

public class RetrieveCookies {

    public static void main(String[] args) {
        try {
            CookieManager cookieManager = new CookieManager();
            CookieHandler.setDefault(cookieManager);

            URL setCookieUrl = new URL("https://httpbin.org/cookies/set?name=xperia");
            HttpURLConnection setConnection = (HttpURLConnection) setCookieUrl.openConnection();
            setConnection.setRequestMethod("GET");
            setConnection.connect();

            BufferedReader reader1 = new BufferedReader(
                    new InputStreamReader(setConnection.getInputStream()));
            while (reader1.readLine() != null) { }
            reader1.close();
            System.out.println("Cookie set successfully.\n");
            
            CookieStore cookieStore = cookieManager.getCookieStore();
            List<HttpCookie> cookies = cookieStore.getCookies();

            System.out.println("Stored Cookies in System:\n");
            for (HttpCookie cookie : cookies) {
                System.out.println("Name: " + cookie.getName());
                System.out.println("Value: " + cookie.getValue());
                System.out.println("Domain: " + cookie.getDomain());
                System.out.println("Path: " + cookie.getPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}