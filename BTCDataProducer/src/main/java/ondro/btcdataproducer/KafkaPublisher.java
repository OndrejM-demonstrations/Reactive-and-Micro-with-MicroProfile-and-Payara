package ondro.btcdataproducer;

import fish.payara.cloud.connectors.kafka.api.KafkaConnection;
import fish.payara.cloud.connectors.kafka.api.KafkaConnectionFactory;
import java.util.logging.Level;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.resource.ConnectionFactoryDefinition;
import ondro.btcdataproducer.util.Logging;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author Ondrej Mihalyi
 */
@ConnectionFactoryDefinition(name = "java:global/env/KafkaConnectionFactory",
        interfaceName = "fish.payara.cloud.connectors.kafka.KafkaConnectionFactory",
        resourceAdapter = "kafka-rar")
@Stateless
public class KafkaPublisher {

    @Resource(lookup = "java:global/env/KafkaConnectionFactory")
    KafkaConnectionFactory factory;
    
    @Inject
    @ConfigProperty(name = "kafka.topic.name", defaultValue = "btctx")
    private String topicName;

    public void sendBtcTxMessage(String value) throws Exception {

        try (KafkaConnection conn = factory.createConnection()) {
            conn.send(new ProducerRecord<>(topicName, value), (RecordMetadata metadata, Exception e) -> {
               if (e != null) {
                   Logging.of(KafkaPublisher.class).log(Level.WARNING, e.getMessage(), e);
               } 
            });
        }
    }
}
