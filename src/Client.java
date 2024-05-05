import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;

public class Client {
    public static void main(String[] args) {
        ArrayList<String> addressList = new ArrayList<>();
        addressList.add("192.168.1.206"); //desktop
        addressList.add("192.168.1.254"); //laptop

        HashMap<String, Greet> greetStubs = new HashMap<>();

        for(String address: addressList) {
            Registry registry = null;
            try {
                registry = LocateRegistry.getRegistry(address, 1099);
            } catch (RemoteException e) {
                System.out.println("There was a problem locating registry for address: " + address);
                return;
            }

            try {
                Greet helloStub = (Greet) registry.lookup("Hello");
                greetStubs.put(address, helloStub);

            } catch (NotBoundException | RemoteException e) {
                System.out.println("There was a binding stubs for address: " + address);
                return;
            }
        }

        for(String address: greetStubs.keySet()) {
            try {
                System.out.println(greetStubs.get(address).sayHello(InetAddress.getLocalHost().getHostName()));
                System.out.println(greetStubs.get(address).sayGoodbye());
            } catch (RemoteException | UnknownHostException e) {
                System.out.println("There was a problem greeting " + address);
            }
        }
    }
}
