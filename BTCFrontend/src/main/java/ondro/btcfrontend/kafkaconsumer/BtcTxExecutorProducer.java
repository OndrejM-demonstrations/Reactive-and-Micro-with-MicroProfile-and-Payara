package ondro.btcfrontend.kafkaconsumer;

import java.util.concurrent.*;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import ondro.btcfrontend.Resource;

@ApplicationScoped
public class BtcTxExecutorProducer {
    
    @Inject
    @Resource
    ThreadFactory threadFactory;
    
    private ExecutorService executor;
    
    @Produces
    @ApplicationScoped
    @BtcTx
    public ExecutorService getExecutor() {
        executor = Executors.newSingleThreadExecutor(threadFactory);
        return executor;
    }
    
    @PreDestroy
    public void shutdown() {
        if (executor != null) {
            executor.shutdown();
        }
    }
}
