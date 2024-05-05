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

        HashMap<String, Hello> helloStubs = new HashMap<>();
        HashMap<String, Goodbye> goodbyeStubs = new HashMap<>();

        for(String address: addressList) {
            Registry registry = null;
            try {
                registry = LocateRegistry.getRegistry("192.168.1.206", 1099);
            } catch (RemoteException e) {
                System.out.println("There was a problem locating registry for address: " + address);
                return;
            }

            try {
                Hello helloStub = (Hello) registry.lookup("Hello");
                helloStubs.put(address, helloStub);

                Goodbye goodbyeStub = (Goodbye) registry.lookup("Goodbye");
                goodbyeStubs.put(address, goodbyeStub);

            } catch (NotBoundException | RemoteException e) {
                System.out.println("There was a binding stubs for address: " + address);
                return;
            }
        }
        for(String address: helloStubs.keySet()) {
            try {
                System.out.println(helloStubs.get(address).sayHello(InetAddress.getLocalHost().getHostName()));
            } catch (RemoteException | UnknownHostException e) {
                System.out.println("There was a problem saying hello for " + address);
            }
        }

        for(String address: goodbyeStubs.keySet()) {
            try {
                System.out.println(goodbyeStubs.get(address).sayGoodbye());
            } catch (RemoteException e) {
                System.out.println("There was a problem saying hello for " + address);
            }
        }
    }
}
