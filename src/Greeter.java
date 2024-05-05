import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Greeter implements Hello, Goodbye {

    public String sayHello(String name) throws RemoteException {
        return "Hello, " + name + "!";
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
