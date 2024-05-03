import java.rmi.RemoteException;

public class Greeter implements Hello, Goodbye {
    public String sayHello(String name) throws RemoteException {
        return "Hello, world!";
    }

    public String sayGoodbye() throws RemoteException {
        return "Goodbye, world!";
    }
}
