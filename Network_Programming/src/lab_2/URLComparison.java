package lab_2;

import java.net.URL;

public class URLComparison {
    public static void main(String[] args) {
        try {
            URL url1a = new URL("http://www.example.org/");
            URL url1b = new URL("http://example.org/");

            URL url2a = new URL("http://www.example.com/index.html#p1");
            URL url2b = new URL("http://www.example.com/index.html#q2");

            System.out.println("Pair (1):");
            System.out.println("equals(): " + url1a.equals(url1b));
            System.out.println("sameFile(): " + url1a.sameFile(url1b));
            System.out.println();

            System.out.println("Pair (2):");
            System.out.println("equals(): " + url2a.equals(url2b));
            System.out.println("sameFile(): " + url2a.sameFile(url2b));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}