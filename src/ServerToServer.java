import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;


public class ServerToServer implements IServerToServer {

    Timer timer = new Timer();
    LocalDateTime lastHeartbeat = null;

    Runnable noHeartbeatAction;
    Runnable heartbeatAction;


    public void setHeartbeatAction(Runnable heartbeatAction) {
        this.heartbeatAction = heartbeatAction;
    }

    public void setNoHeartbeatAction(Runnable noHeartbeatAction) {
        this.noHeartbeatAction = noHeartbeatAction;
    }

    @Override
    public void beatHeart(String address) {
        lastHeartbeat = LocalDateTime.now();
        System.out.println("Received heartbeat from: " + address);
        heartbeatAction.run();
        scheduleNoHeartbeatCheck();
    }

    private void scheduleNoHeartbeatCheck() {
        if (noHeartbeatAction == null) {
            return;
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (Duration.between(lastHeartbeat, LocalDateTime.now()).getSeconds() >= 5) {
                    noHeartbeatAction.run();
                }
            }
        }, 5000);
    }
}
