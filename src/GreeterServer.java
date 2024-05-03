import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class GreeterServer {
    public static void main(String[] args) {
        try {
            // Create an instance of the remote object
            Greeter server = new Greeter();
            UnicastRemoteObject.exportObject(server, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry("127.0.0.1",1099); // Registry port
            registry.rebind("Hello", (Hello) server);
            registry.rebind("Goodbye", (Goodbye) server);

            System.out.println("Server started");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
