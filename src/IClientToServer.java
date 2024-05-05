import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClientToServer extends Remote {
    void subscribeToServerNotifications(String address) throws RemoteException;

    String sayHello(String name) throws RemoteException;

    String sayGoodbye() throws RemoteException;
}