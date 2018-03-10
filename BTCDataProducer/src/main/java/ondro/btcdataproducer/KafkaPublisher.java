package ondro.btcdataproducer;

import fish.payara.cloud.connectors.kafka.api.KafkaConnection;
import fish.payara.cloud.connectors.kafka.api.KafkaConnectionFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.resource.ConnectionFactoryDefinition;
import javax.resource.spi.TransactionSupport.TransactionSupportLevel;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 *
 * @author Ondrej Mihalyi
 */
@ConnectionFactoryDefinition(name = "java:global/env/KafkaConnectionFactory",
        description = "Kafka Conn Factory",
        interfaceName = "fish.payara.cloud.connectors.kafka.KafkaConnectionFactory",
        resourceAdapter = "kafka-rar",
        minPoolSize = 2,
        maxPoolSize = 2,
        transactionSupport = TransactionSupportLevel.NoTransaction,
        properties = {})
@Stateless
public class KafkaPublisher {

    @Resource(lookup = "java:global/env/KafkaConnectionFactory")
    KafkaConnectionFactory factory;

    public void sendMessage() throws Exception {

        try (KafkaConnection conn = factory.createConnection()) {
            conn.send(new ProducerRecord("test", "hello", "world"));
        }
    }
}
