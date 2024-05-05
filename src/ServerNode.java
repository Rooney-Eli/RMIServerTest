import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;

public class ServerNode {
    private static ClientToServer server;

    public static void main(String[] args) {
        server = setupServer();
        if (server == null) return;

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                notifySubscribers();
            }
        }, 0, 5000);

    }
    static int num = 0;
    public static void notifySubscribers() {
        server.notifySubscribersNewNumber(num++);
    }

    public static ClientToServer setupServer() {
        ClientToServer server;
        try {
            server = new ClientToServer();
            UnicastRemoteObject.exportObject(server, 0);
        } catch (RemoteException e) {
            System.err.println("There was a problem exporting remote server object!");
            return null;
        }

        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1",1099); // Local Registry for the Node
            registry.rebind("Hello", server);
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
