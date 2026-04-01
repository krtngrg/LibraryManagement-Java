package lab_2;

import java.net.URL;
import java.net.URLEncoder;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class HTTPGet {
    public static void main(String[] args) {
        try {
            
            String query = URLEncoder.encode("Java networking", "UTF-8");
            URL url = new URL("https://www.google.com/search?q=" + query);

            InputStream in = new BufferedInputStream(url.openStream());
            Reader r = new InputStreamReader(in);
            int c;
            int count = 0;

            while ((c = r.read()) != -1 && count < 300) {
                System.out.print((char) c);
                count++;
            }

            r.close();
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
