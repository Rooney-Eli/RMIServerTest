import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServerToServer extends Remote {

    public void beatHeart(String address) throws RemoteException;

}
