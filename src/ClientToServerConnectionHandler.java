import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientToServerConnectionHandler extends ServerConnectionHandler {

    HashMap<String, IClientToServer> clientToServerStubs;
    String myAddress;

    public ClientToServerConnectionHandler(String myAddress, ArrayList<String> addresses, String registryName) {
        this.clientToServerStubs = connectToServers(addresses, registryName, IClientToServer.class);
        this.myAddress = myAddress;
        setupNotificationServer();
    }

    public void invokeSayHello() {
        for(String address: clientToServerStubs.keySet()) {
            try {
                System.out.println(clientToServerStubs.get(address).sayHello(InetAddress.getLocalHost().getHostName()));
                System.out.println(clientToServerStubs.get(address).sayGoodbye());
            } catch (RemoteException | UnknownHostException e) {
                System.out.println("There was a problem greeting " + address);
            }
        }
    }

    public void invokeSubscribeToServers() {

        for(String address: clientToServerStubs.keySet()) {
            try {
                clientToServerStubs.get(address).subscribeToServerNotifications(myAddress);
            } catch (RemoteException e) {
                System.out.println("There was a problem greeting " + address);
            }
        }
    }
}
