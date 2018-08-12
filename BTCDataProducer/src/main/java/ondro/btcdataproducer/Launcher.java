package ondro.btcdataproducer;

import com.pusher.client.connection.*;
import java.util.logging.Level;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import ondro.btcdataproducer.entity.AppEventType;
import ondro.btcdataproducer.entity.ApplicationEvent;
import ondro.btcdataproducer.util.Logging;

/**
 *
 * @author Ondrej Mihalyi
 */
@ApplicationScoped
public class Launcher {

    @Resource
    private ManagedScheduledExecutorService executor;

    @Inject
    @BtcTx
    private Event<String> btcTxEvent;

    private BitstampConnector bitstampConnector;

    Jsonb jsonb = JsonbBuilder.create();

    public void start(@Observes @Initialized(ApplicationScoped.class) Object context) {
        executor.submit(() -> {
            bitstampConnector = new BitstampConnector();
            bitstampConnector.connect(data -> {
                try {
                    btcTxEvent.fireAsync(data);
                } catch (Exception ex) {
                    Logging.of(this).log(Level.SEVERE, null, ex);
                    throw new RuntimeException();
                }
            }, executor, new ConnectionEventListener() {
                @Override
                public void onConnectionStateChange(ConnectionStateChange change) {
                    if (change.getCurrentState().compareTo(ConnectionState.CONNECTED) == 0) {
                        try {
                            String btcRateConnected = jsonb.toJson(
                                    new ApplicationEvent(AppEventType.BTC_RATE_CONNECTED));
                            btcTxEvent.fireAsync(btcRateConnected);
                        } catch (Exception ex) {
                            Logging.of(this).log(Level.SEVERE, null, ex);
                            throw new RuntimeException();
                        }
                    }
                }

                @Override
                public void onError(String message, String code, Exception e) {
                }
            });
        });
    }

    @PreDestroy
    private void cleanup() {
        if (bitstampConnector != null) {
            bitstampConnector.disconnect();
        }
    }
}
