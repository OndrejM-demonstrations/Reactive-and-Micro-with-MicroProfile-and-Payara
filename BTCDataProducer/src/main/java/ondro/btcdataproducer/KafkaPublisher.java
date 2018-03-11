package ondro.btcdataproducer;

import fish.payara.cloud.connectors.kafka.api.KafkaConnection;
import fish.payara.cloud.connectors.kafka.api.KafkaConnectionFactory;
import java.util.concurrent.Future;
import java.util.logging.Level;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.resource.ConnectionFactoryDefinition;
import javax.resource.spi.TransactionSupport.TransactionSupportLevel;
import ondro.btcdataproducer.util.Logging;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author Ondrej Mihalyi
 */
@ConnectionFactoryDefinition(name = "java:global/env/KafkaConnectionFactoryx",
        description = "Kafka Conn Factory",
        interfaceName = "fish.payara.cloud.connectors.kafka.KafkaConnectionFactory",
        resourceAdapter = "kafka-rar",
        minPoolSize = 2,
        maxPoolSize = 2,
        transactionSupport = TransactionSupportLevel.NoTransaction,
        properties = {"requestTimeout=1000", "maxBlockMS=1000"})
@Stateless
public class KafkaPublisher {

    @Resource(lookup = "java:global/env/KafkaConnectionFactoryx")
    KafkaConnectionFactory factory;
    
    @Inject
    @ConfigProperty(name = "kafka.topic.name", defaultValue = "btctx")
    private String topicName;

    public void sendMessage(String value) throws Exception {

        try (KafkaConnection conn = factory.createConnection()) {
            conn.send(new ProducerRecord<>(topicName, value), (RecordMetadata metadata, Exception e) -> {
               if (e != null) {
                   Logging.of(KafkaPublisher.class).log(Level.WARNING, e.getMessage(), e);
               } 
            });
        }
    }
}
