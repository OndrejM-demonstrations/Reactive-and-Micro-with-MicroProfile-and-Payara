package ondro.btcdataproducer;

import fish.payara.cloud.connectors.kafka.api.KafkaConnection;
import fish.payara.cloud.connectors.kafka.api.KafkaConnectionFactory;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.resource.ConnectionFactoryDefinition;
import javax.resource.spi.TransactionSupport.TransactionSupportLevel;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.eclipse.microprofile.config.inject.ConfigProperty;

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
    
    @Inject
    @ConfigProperty(name = "kafka.topic.name", defaultValue = "btctx")
    private String topicName;

    public void sendMessage(Object value) throws Exception {

        try (KafkaConnection conn = factory.createConnection()) {
            conn.send(new ProducerRecord(topicName, value));
        }
    }
}
