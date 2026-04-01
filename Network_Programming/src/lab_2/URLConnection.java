package lab_2;

import java.net.URL;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class URLConnection {
    public static void main(String[] args) {
        try {
            URL u = new URL("http://www.example.com/");

            InputStream in = new BufferedInputStream(u.openStream());
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
