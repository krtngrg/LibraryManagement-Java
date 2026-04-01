package RMIcall;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessageInterface extends Remote {
    String HelloMessage() throws RemoteException;
}
