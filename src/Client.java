import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class Client {
    public static void main(String[] args) {
        String myAddress = "192.168.1.206";

        ArrayList<String> addressList = new ArrayList<>();
        addressList.add("192.168.1.206"); //desktop
        addressList.add("192.168.1.254"); //laptop

        HashMap<String, IClientToServer> clientToServerStubs = connectToServers(addressList);

        setupNotificationServer();

        for(String address: clientToServerStubs.keySet()) {
            try {
                System.out.println(clientToServerStubs.get(address).sayHello(InetAddress.getLocalHost().getHostName()));
                System.out.println(clientToServerStubs.get(address).sayGoodbye());
            } catch (RemoteException | UnknownHostException e) {
                System.out.println("There was a problem greeting " + address);
            }
        }

        for(String address: clientToServerStubs.keySet()) {
            try {
                clientToServerStubs.get(address).subscribeToServerNotifications(myAddress);
            } catch (RemoteException e) {
                System.out.println("There was a problem greeting " + address);
            }
        }

    }


    public static void setupNotificationServer() {
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

    public static HashMap<String, IClientToServer> connectToServers(ArrayList<String> addressList) {
        HashMap<String, IClientToServer> clientToServerStubs = new HashMap<>();

        for(String address: addressList) {
            Registry registry;
            try {
                registry = LocateRegistry.getRegistry(address, 1099);
            } catch (RemoteException e) {
                System.out.println("There was a problem locating registry for address: " + address);
                continue;
            }

            try {
                IClientToServer clientToServerStub = (IClientToServer) registry.lookup("Hello");
                clientToServerStubs.put(address, clientToServerStub);

            } catch (NotBoundException | RemoteException e) {
                System.out.println("There was a binding stubs for address: " + address);
            }
        }
        return clientToServerStubs;
    }


}
