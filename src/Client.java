import java.util.ArrayList;

public class Client {
    public static void main(String[] args) {
        String myAddress = "192.168.1.206";

        ArrayList<String> serverAddresses = new ArrayList<>();
        serverAddresses.add("192.168.1.206"); //desktop
//        serverAddresses.add("192.168.1.254"); //laptop

        ClientToServerConnectionHandler cts = new ClientToServerConnectionHandler(myAddress, serverAddresses, "CTS");
        cts.invokeSubscribeToServers();
        cts.invokeSayHello();

    }







}
