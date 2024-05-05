import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class ClientToServer implements IClientToServer {
    HashMap<String, IServerToClient> subscriptions = new HashMap<>();

    public void notifySubscribersNewNumber(int num) {
        for(String address: subscriptions.keySet()) {
            try {
                subscriptions.get(address).giveClientANumber(num);
            } catch (RemoteException e) {
                System.err.println("There was an error when notifying: " + address);
            }
        }
    }

    public void subscribeToServerNotifications(String address) throws RemoteException {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(address, 1099);
        } catch (RemoteException e) {
            System.out.println("There was a problem locating registry for address: " + address);
            return;
        }

        try {
            IServerToClient serverToClientStub = (IServerToClient) registry.lookup("Notifications");
            subscriptions.put(address, serverToClientStub);
        } catch (NotBoundException | RemoteException e) {
            System.out.println("There was a binding stubs for address: " + address);
            return;
        }
    }

    public String sayHello(String name) throws RemoteException {
        return "Hello, from " + name + "!";
    }

    public String sayGoodbye() throws RemoteException {
        try {
            System.out.println("Saying goodbye.");
            return "Goodbye, from " + InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            System.err.println("Couldn't respond with host name for a goodbye!");
        }
        return "Goodbye, from someone??";
    }
}
