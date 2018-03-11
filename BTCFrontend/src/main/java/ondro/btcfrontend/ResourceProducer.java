package ondro.btcfrontend;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.ExecutorService;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Ondrej Mihalyi
 */
@ApplicationScoped
public class ResourceProducer {
    
    @Produces
    @Resource
    @ApplicationScoped
    public ExecutorService getDefaultExecutorService() throws NamingException {
        return (ExecutorService)(new InitialContext().lookup("java:comp/DefaultManagedExecutorService"));
    }
    
    @Produces
    @Resource
    @ApplicationScoped
    public Scheduler getDefaultScheduler(@Resource ExecutorService executor) {
        return Schedulers.from(executor);
    }
}

