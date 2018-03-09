package ondro.btcfrontend;

import java.net.URL;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * REST Web Service
 *
 * @author Ondrej Mihalyi
 */
@Path("rate")
@RequestScoped
public class RateResource {

    @Inject
    @ConfigProperty( name = "backend.rate.url", defaultValue = "http://localhost:8080/BTCBackend/rest/rate")
    private URL rateUrl;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getRateFromBackend(@Suspended AsyncResponse response) {
        return "1.0";
    }

}
