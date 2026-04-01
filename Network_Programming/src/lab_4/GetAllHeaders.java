package lab_4;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class GetAllHeaders {

    public static void main(String[] args) {
        try {
            URL u = new URL("https://www.google.com");
            
            URLConnection uc = u.openConnection();
            InputStream is = uc.getInputStream();
            is.close();
            
            int i = 0;
            String headerKey, headerValue;
            while (true) {
                headerKey = uc.getHeaderFieldKey(i);
                headerValue = uc.getHeaderField(i);
                if (headerKey == null && headerValue == null)
                    break;

                if (headerKey == null)
                    System.out.println(headerValue); 
                else
                    System.out.println(headerKey + " : " + headerValue);
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
