package ondro.btcfrontend;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.ExecutorService;
import javax.annotation.PreDestroy;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedThreadFactory;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

/**
 *
 * @author Ondrej Mihalyi
 */
@ApplicationScoped
public class ResourceProducer {
    
    @Produces
    @Resource
    @javax.annotation.Resource
    ManagedExecutorService executorService;
    
    @Produces
    @Resource
    @javax.annotation.Resource
    ManagedThreadFactory threadFactory;
    
    private Scheduler scheduler;

    @Produces
    @Resource
    @ApplicationScoped
    public Scheduler getDefaultScheduler(@Resource ExecutorService executor) {
        scheduler = Schedulers.from(executor);
        return scheduler;
    }
    
    @PreDestroy
    public void shutdown() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }
}

