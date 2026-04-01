package RMIcall;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIMessageClient {
    public static void main(String[] args) {
        try{
            Registry registry = LocateRegistry.getRegistry("localhost", 8848);

            MessageInterface stub = (MessageInterface) registry.lookup("RMIMessageService");
            System.out.println(stub.HelloMessage());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
