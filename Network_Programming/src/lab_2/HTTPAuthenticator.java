package lab_2;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.HttpURLConnection;

public class HTTPAuthenticator {

    public static void main(String[] args) {
        try {
            Authenticator.setDefault(new MyAuthenticator());
            URL url = new URL("https://httpbin.org/basic-auth/admin/Admin@123");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            System.out.println("Response code: " + conn.getResponseCode());
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    static class MyAuthenticator extends Authenticator {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            String username = "admin";
            char[] password = "Admin@123".toCharArray();
            PasswordAuthentication auth = new PasswordAuthentication(username, password);

            java.util.Arrays.fill(password, ' ');

            return auth;
        }
    }
}
