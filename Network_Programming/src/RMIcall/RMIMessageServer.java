package RMIcall;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIMessageServer {
    public static void main(String[] args) {
        try{
            MessageInterface serverObj = new MessageService();
            Registry registry = LocateRegistry.createRegistry(8848);
            registry.rebind("RMIMessageService", serverObj);

            System.out.println("RMI message server is running");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}


