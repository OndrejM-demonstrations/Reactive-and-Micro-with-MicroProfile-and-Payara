package ondro.btcdataproducer;

import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Ondrej Mihalyi
 */
@RunWith(Arquillian.class)
public class KafkaPublisherTest {

    @Inject
    private KafkaPublisher publisher;

    @Deployment
    public static WebArchive testedCode() {
        return ShrinkWrap.create(WebArchive.class)
                .addClass(KafkaPublisher.class)
                .addPackages(true, "fish.payara.cloud.connectors.kafka");
    }

    @Test
    public void canSendMessage() throws Exception {
        publisher.sendMessage();
    }
}
