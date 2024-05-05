import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerConnectionHandler {

    public ServerConnectionHandler() {

    }


    public void setupNotificationServer() {
        ServerToClient notificationServer;
        try {
            notificationServer = new ServerToClient();
            UnicastRemoteObject.exportObject(notificationServer, 0);
        } catch (RemoteException e) {
            System.err.println("There was a problem exporting remote notificationServer object!");
            return;
        }

        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1",1099); // Local Registry for the Node
            registry.rebind("Notifications", notificationServer);
        } catch (RemoteException e) {
            System.err.println("There was a problem binding the server object in the registry!");
        }
    }

    public static <T extends Remote> HashMap<String, T> connectToServers(ArrayList<String> addressList, String registryName, Class<T> stubClass) {
        HashMap<String, T> stubs = new HashMap<>();

        for (String address : addressList) {
            Registry registry;
            try {
                registry = LocateRegistry.getRegistry(address, 1099);
            } catch (RemoteException e) {
                System.out.println("There was a problem locating registry for address: " + address);
                continue;
            }

            try {
                T stub = stubClass.cast(registry.lookup(registryName));
                stubs.put(address, stub);
            } catch (NotBoundException | RemoteException e) {
                System.out.println("There was a binding stubs for address: " + address);
            }
        }
        return stubs;
    }
}
