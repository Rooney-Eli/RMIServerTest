import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Greeter implements Hello, Goodbye {

    public String sayHello(String name) throws RemoteException {
        return "Hello, " + name + "!";
    }

    public String sayGoodbye() throws RemoteException {
        return "Goodbye, world!";
    }
}
