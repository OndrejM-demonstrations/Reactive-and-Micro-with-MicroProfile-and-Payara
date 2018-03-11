package ondro.btcdataproducer;

import java.util.logging.Level;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import ondro.btcdataproducer.util.Logging;

/**
 *
 * @author Ondrej Mihalyi
 */
@Dependent
public class Launcher {
    
    @Resource
    private ManagedScheduledExecutorService executor;
    
    @Inject
    private KafkaPublisher publisher;
    
    public void start(@Observes @Initialized(ApplicationScoped.class) Object context) {
        new BitstampConnector().connect(data -> {
            try {
                publisher.sendMessage(data);
            } catch (Exception ex) {
                Logging.of(this).log(Level.SEVERE, null, ex);
                throw new RuntimeException();
            }
        }, executor);
    }
}

