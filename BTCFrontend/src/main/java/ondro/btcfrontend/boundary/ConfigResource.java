package ondro.btcfrontend.boundary;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import ondro.btcfrontend.entity.PageConfig;

/**
 *
 * @author Ondrej Mihalyi
 */
@Path("config")
@RequestScoped
public class ConfigResource {

    @Inject
    private PageConfig config;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PageConfig getConfig() {
        return config;
    }

}

