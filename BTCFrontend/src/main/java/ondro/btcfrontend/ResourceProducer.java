package ondro.btcfrontend;

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
    public ExecutorService getDefaultScheduledExecutorService() throws NamingException {
        return (ExecutorService)(new InitialContext().lookup("java:comp/DefaultManagedExecutorService"));
    }
}

