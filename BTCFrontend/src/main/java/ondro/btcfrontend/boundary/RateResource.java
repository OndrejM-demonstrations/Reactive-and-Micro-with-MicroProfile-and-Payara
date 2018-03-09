package ondro.btcfrontend.boundary;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import ondro.btcfrontend.entity.BitstampTicker;
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
    @ConfigProperty(name = "bitstamp.ticker.url", defaultValue = "https://www.bitstamp.net/api/ticker/")
    private URL tickerUrl;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void getRateFromBackend(@Suspended AsyncResponse response) throws URISyntaxException {
        CompletableFuture<BitstampTicker> cfTicker = new CompletableFuture<>();
        ClientBuilder.newClient()
                .target(tickerUrl.toURI())
                .request()
                .async()
                .get(new InvocationCallback<BitstampTicker>() {
                    @Override
                    public void completed(BitstampTicker response) {
                        cfTicker.complete(response);
                    }

                    @Override
                    public void failed(Throwable throwable) {
                        cfTicker.completeExceptionally(throwable);
                    }
                });
        cfTicker
        .thenApply(
            ticker -> String.valueOf(ticker.getLast()))
        .thenAccept(rate -> {
            response.resume(rate);
        }).exceptionally(e -> {
            response.resume(e);
            return null;
        });
    }

}
