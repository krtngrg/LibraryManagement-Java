package lab_2;

import java.net.URLEncoder;
import java.net.URLDecoder;

public class URLEncodeDecode {
    public static void main(String[] args) {
        try {
            String text = "This string has spaces & é symbols";

            String encoded = URLEncoder.encode(text, "UTF-8");
            System.out.println("Encoded string: " + encoded);
            String decoded = URLDecoder.decode(encoded, "UTF-8");
            System.out.println("Decoded string: " + decoded);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
