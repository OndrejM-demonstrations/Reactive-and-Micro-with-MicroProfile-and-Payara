package ondro.btcfrontend;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import ondro.btcfrontend.entity.BitstampTicker;
import static org.assertj.core.api.Assertions.*;
import org.testng.annotations.Test;

/**
 *
 * @author Ondrej Mihalyi
 */
public class BitstampTest {

    @Test
    public void canRetrieveBtcRate() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://www.bitstamp.net/api/ticker/");
        BitstampTicker ticker = target.request().get(BitstampTicker.class);
        assertThat(ticker).isNotNull();
        assertThat(ticker.getLast()).isNotNull();
    }

}
