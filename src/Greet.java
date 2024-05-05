import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Greet extends Remote {
    String sayHello(String name) throws RemoteException;
    String sayGoodbye() throws RemoteException;
}