package ondro.btcdataproducer;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Ondrej Mihalyi
 */
public class BistampConnectorTest {

    @Test
    public void canGetTxFromBitstampStream() {
        Thread mainThread = Thread.currentThread();
        BitstampConnector connector = new BitstampConnector();

        connector.connect(data -> {
            Logger.getGlobal().info("Received event with data: " + data);
            connector.disconnect();
            mainThread.interrupt();
        }, new ScheduledThreadPoolExecutor(1));

        try {
            Thread.sleep(30000);
            Assert.fail("No event received before timeout");
        } catch (InterruptedException ex) {
            Logger.getGlobal().info("Received expected events, test passed");
        }
    }
}
