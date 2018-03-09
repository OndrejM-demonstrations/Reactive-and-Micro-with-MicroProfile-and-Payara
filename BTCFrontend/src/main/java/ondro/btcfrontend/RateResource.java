package ondro.btcfrontend;

import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Ondrej Mihalyi
 */
@Path("rate")
public class RateResource {


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getValue() {
        return "1.0";
    }

}
