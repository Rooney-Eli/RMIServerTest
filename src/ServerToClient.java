import java.rmi.RemoteException;
import java.util.HashMap;

public class ServerToClient implements IServerToClient {

    @Override
    public void giveClientANumber(int num) throws RemoteException {
        System.out.println("Client received: " + num);
    }
}
