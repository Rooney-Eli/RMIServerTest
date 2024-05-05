import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class GreeterServer {
    public static void main(String[] args) {

        Greeter server;
        try {
            server = new Greeter();
            UnicastRemoteObject.exportObject(server, 0);
        } catch (RemoteException e) {
            System.err.println("There was a problem exporting remote server object!");
            return;
        }

        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1",1099); // Local Registry for the Node
            registry.rebind("Hello", (Greet) server);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("There was a problem binding the server object in the registry!");
            return;
        }

        try {
            System.out.println("Server started on: " + InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            System.err.println("Server started, but can't determine the host!");
        }
    }
}
