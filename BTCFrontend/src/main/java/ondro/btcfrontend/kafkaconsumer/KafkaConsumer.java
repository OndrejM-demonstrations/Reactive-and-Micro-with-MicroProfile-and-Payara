package ondro.btcfrontend.kafkaconsumer;

import fish.payara.cloud.connectors.kafka.api.KafkaListener;
import fish.payara.cloud.connectors.kafka.api.OnRecord;
import io.reactivex.FlowableEmitter;
import java.util.concurrent.ExecutorService;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * Example Apache Kafka MDB
 *
 * @author Steve Millidge (Payara Foundation)
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "topics", propertyValue = "btctx"),
    @ActivationConfigProperty(propertyName = "groupIdConfig", propertyValue = "testGroup"),
    @ActivationConfigProperty(propertyName = "bootstrapServersConfig", propertyValue = "localhost:9092"),
    @ActivationConfigProperty(propertyName = "keyDeserializer", propertyValue = "org.apache.kafka.common.serialization.StringDeserializer"),
    @ActivationConfigProperty(propertyName = "valueDeserializer", propertyValue = "org.apache.kafka.common.serialization.StringDeserializer")
})
public class KafkaConsumer implements KafkaListener {

    @Inject
    @BtcTx
    FlowableEmitter<String> emitter;
    
    @Inject
    @BtcTx
    ExecutorService sequentialExecutor;
    
    @OnRecord
    public void processBtcTxMessage(ConsumerRecord<Object, String> record) {
        System.out.println("Got record on topic btctx " + record);
        // onNext must be called sequentially because it's not thread-safe
        sequentialExecutor.submit( () -> {
            emitter.onNext(record.value());
        });
    }

}
