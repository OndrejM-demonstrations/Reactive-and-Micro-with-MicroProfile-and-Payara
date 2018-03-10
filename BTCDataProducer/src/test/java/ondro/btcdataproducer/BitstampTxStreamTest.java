package ondro.btcdataproducer;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;
import org.junit.Assert;

/**
 *
 * @author Ondrej Mihalyi
 */
public class BitstampTxStreamTest {

    @Test
    public void canGet5TxFromBitstampStream() {
        
        Thread mainThread = Thread.currentThread();
        
        PusherOptions options = new PusherOptions();
        final String BITSTAMP_KEY = "de504dc5763aeef9ff52";
        Pusher pusher = new Pusher(BITSTAMP_KEY, options);

        pusher.connect(new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange change) {
                System.out.println("State changed to " + change.getCurrentState()
                        + " from " + change.getPreviousState());
            }

            @Override
            public void onError(String message, String code, Exception e) {
                System.out.println("There was a problem connecting!");
            }
        }, ConnectionState.ALL);

// Subscribe to a channel
        Channel channel = pusher.subscribe("live_trades");

// Bind to listen for events called "my-event" sent to "my-channel"
        channel.bind("trade", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channel, String event, String data) {
                System.out.println("Received event with data: " + data);
                mainThread.interrupt();
            }
        });
        
        try {
            Thread.sleep(10000);
            Assert.fail("No event received before timeout");
        } catch (InterruptedException ex) {
        }
    }
}
