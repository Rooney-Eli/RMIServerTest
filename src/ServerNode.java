import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ServerNode {
    static HashMap<String, IServerToClient> subscriptions = new HashMap<>();


    public static void main(String[] args) {
        String myAddress = "192.168.1.206";

        ArrayList<String> serverAddresses = new ArrayList<>();
        serverAddresses.add("192.168.1.206"); //desktop
        serverAddresses.add("192.168.1.254"); //laptop

        ClientToServer clientToServerStub = setupServer(subscriptions);
        if (clientToServerStub == null) return;

        serverAddresses.remove(myAddress);
        ServerToServerConnectionHandler sts = new ServerToServerConnectionHandler(myAddress, serverAddresses, "STS");


    }

    public static ClientToServer setupServer(HashMap<String, IServerToClient> subscriptions) {
        ClientToServer server;
        try {
            server = new ClientToServer(subscriptions);
            UnicastRemoteObject.exportObject(server, 0);
        } catch (RemoteException e) {
            System.err.println("There was a problem exporting remote server object!");
            return null;
        }

        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1",1099); // Local Registry for the Node
            registry.rebind("CTS", server);
        } catch (RemoteException e) {
            System.err.println("There was a problem binding the server object in the registry!");
            return null;
        }

        try {
            System.out.println("Server started on: " + InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            System.err.println("Server started, but can't determine the host!");
        }
        return server;
    }




}
