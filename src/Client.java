import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        try {
            // Get a reference to the RMI registry running on the server's host and port
            Registry registry = LocateRegistry.getRegistry("192.168.1.254", 1099); // Server's host and port

            // Look up the remote object by name
            Hello helloStub = (Hello) registry.lookup("Hello");
            Goodbye goodbyeStub = (Goodbye) registry.lookup("Goodbye");

            // Call the remote method
            String helloResponse = helloStub.sayHello("bob");
            System.out.println("Response from server: " + helloResponse);

            String goodbyeResponse = goodbyeStub.sayGoodbye();
            System.out.println("Response from server: " + goodbyeResponse);

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
