package lab_2;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class ConditionalProxy {

    public static void main(String[] args) {
        try {
            
            ProxySelector.setDefault(new MyProxySelector());
            URI url1 = new URI("http://www.example.com/confidential/data.html");
            URI url2 = new URI("http://www.example.com/public/info.html");

            System.out.println("Proxies for " + url1 + ": " + ProxySelector.getDefault().select(url1));
            System.out.println("Proxies for " + url2 + ": " + ProxySelector.getDefault().select(url2));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static class MyProxySelector extends ProxySelector {

        private Proxy myProxy;

        public MyProxySelector() {
            
            myProxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8080));
        }

        @Override
        public List<Proxy> select(URI uri) {
            if (uri.getPath() != null && uri.getPath().startsWith("/confidential")) {
                
                return Collections.singletonList(myProxy);
            } else {
                
                return Collections.singletonList(Proxy.NO_PROXY);
            }
        }

        @Override
        public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
            System.out.println("Connection failed to " + uri + " via proxy " + sa + ": " + ioe.getMessage());
        }
    }
}
