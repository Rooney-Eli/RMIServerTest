import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServerToClient extends Remote {
    void giveClientANumber(int num) throws RemoteException;
}
