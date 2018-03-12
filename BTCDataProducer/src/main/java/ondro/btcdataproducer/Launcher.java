package ondro.btcdataproducer;

import java.util.logging.Level;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
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
    private KafkaPublisher publisher;

    private BitstampConnector bitstampConnector;

    public void start(@Observes @Initialized(ApplicationScoped.class) Object context) {
        executor.submit(() -> {
            bitstampConnector = new BitstampConnector();
            bitstampConnector.connect(
                    data -> {
                        try {
                            publisher.sendBtcTxMessage(data);
                        } catch (Exception ex) {
                            Logging.of(this).log(Level.SEVERE, null, ex);
                            throw new RuntimeException();
                        }
                    }, executor);
        });
    }

    @PreDestroy
    private void cleanup() {
        if (bitstampConnector != null) {
            bitstampConnector.disconnect();
        }
    }
}
