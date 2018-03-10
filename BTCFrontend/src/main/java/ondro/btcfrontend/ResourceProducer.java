package ondro.btcfrontend;

import java.util.concurrent.ScheduledExecutorService;
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
    public ScheduledExecutorService getDefaultScheduledExecutorService() throws NamingException {
        return (ScheduledExecutorService)(new InitialContext().lookup("java:comp/DefaultManagedScheduledExecutorService"));
    }
}

