package ondro.btcfrontend;

import java.net.URISyntaxException;
import java.net.URL;
import javax.ws.rs.client.ClientBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import org.glassfish.jersey.client.rx.rxjava2.RxFlowableInvokerProvider;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Ondrej Mihalyi
 */
@RunWith(Arquillian.class)
public class RateResourceTest {

    @Deployment(testable = false)
    public static WebArchive testedCode() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, RestApplication.class.getPackage())
                .addPackages(true, io.reactivex.FlowableEmitter.class.getPackage())
                .addPackages(true, org.reactivestreams.Publisher.class.getPackage())
                .addPackages(true, RxFlowableInvokerProvider.class.getPackage());
    }

    @Test
    public void canGetRateViaRest(@ArquillianResource URL baseUrl) throws URISyntaxException {
        System.out.println("URL: " + baseUrl);
        String rate = ClientBuilder.newClient()
                .target(baseUrl.toURI())
                .path("rest").path("rate")
                .request().get(String.class);
        assertThat(rate).isNotNull();
    }
}
