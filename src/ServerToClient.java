import java.rmi.RemoteException;

public class ServerToClient implements IServerToClient {
    public void giveClientANumber(int num) throws RemoteException {
        System.out.println("Client received: " + num);
    }
}
