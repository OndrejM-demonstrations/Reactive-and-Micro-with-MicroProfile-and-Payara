package ondro.btcdataproducer;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;
import java.util.logging.Logger;
import org.junit.Test;
import org.junit.Assert;

/**
 *
 * @author Ondrej Mihalyi
 */
public class BitstampTxStreamTest {

    public static final String BITSTAMP_APP_KEY = "de504dc5763aeef9ff52";

    @Test
    public void canGetTxFromBitstampStream() {

        Thread mainThread = Thread.currentThread();

        Pusher pusher = createBitstampTradesPusher(new SubscriptionEventListener() {
            @Override
            public void onEvent(String channel, String event, String data) {
                Logger.getGlobal().info("Received event with data: " + data);
                mainThread.interrupt();
            }
        });
        connectTo(pusher);
        
        try {
            Thread.sleep(30000);
            Assert.fail("No event received before timeout");
        } catch (InterruptedException ex) {
        }
    }

    private Pusher createBitstampTradesPusher(SubscriptionEventListener subscriptionEventListener) {
        PusherOptions options = new PusherOptions();
        Pusher pusher = new Pusher(BITSTAMP_APP_KEY, options);
        // Subscribe to a channel
        Channel channel = pusher.subscribe("live_trades");
        // Bind to listen for events called "my-event" sent to "my-channel"
        channel.bind("trade", subscriptionEventListener);
        return pusher;
    }

    private void connectTo(Pusher pusher) {
        pusher.connect(new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange change) {
                Logger.getGlobal().info("State changed to " + change.getCurrentState()
                        + " from " + change.getPreviousState());
            }

            @Override
            public void onError(String message, String code, Exception e) {
                Logger.getGlobal().info("There was a problem connecting!");
            }
        }, ConnectionState.ALL);
    }
}
