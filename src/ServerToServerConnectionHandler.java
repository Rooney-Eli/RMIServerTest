import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class ServerToServerConnectionHandler extends ServerConnectionHandler {

    HashMap<String, IServerToServer> serverToServerStubs;
    HashMap<String, Boolean> heartbeatStatuses;
    String myAddress;
    Timer timer;

    public ServerToServerConnectionHandler(String myAddress, ArrayList<String> addresses, String registryName) {
        this.serverToServerStubs = connectToServers(addresses, registryName, IServerToServer.class);
        setupForHeartbeats(this.serverToServerStubs);
        this.myAddress = myAddress;
        setupNotificationServer();

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                invokeBeatHeart();
            }
        }, 0, 2000);
    }

    private void setupForHeartbeats(HashMap<String, IServerToServer> stubs) {
        for (String address : stubs.keySet()) {
            registerNoHeartbeatAction(address, (ServerToServer) stubs.get(address));
            registerHeartbeatAction(address, (ServerToServer) stubs.get(address));
            heartbeatStatuses.put(address, true);
        }
    }

    private void registerHeartbeatAction(String address, ServerToServer stub) {

        // Instead of passing data reference to be mutated just pass a function which mutates the data
        Runnable heartbeatAction = () -> {
            System.out.println("Received Heartbeat for : " + address);
            heartbeatStatuses.put(address, true);
        };
        stub.setHeartbeatAction(heartbeatAction);
    }

    private void registerNoHeartbeatAction(String address, ServerToServer stub) {

        // Instead of passing data reference to be mutated just pass a function which mutates the data
        Runnable noHeartbeatAction = () -> {
            System.out.println("No heartbeat received within 5 seconds for: " + address);
            heartbeatStatuses.put(address, false);
        };
        stub.setNoHeartbeatAction(noHeartbeatAction);
    }


    public void invokeBeatHeart() {

        // We "send out" a heartbeat for this server so the other servers know we are alive
        for (String address : serverToServerStubs.keySet()) {

            // Don't bother with nodes who you think are dead
            if(heartbeatStatuses.get(address)) {
                try {
                    serverToServerStubs.get(address).beatHeart(myAddress);
                } catch (RemoteException e) {
                    System.out.println("There was a problem invoking heartbeat for " + address);
                }
            }
        }
    }

}
