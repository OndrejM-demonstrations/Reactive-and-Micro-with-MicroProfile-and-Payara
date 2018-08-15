package ondro.btcfrontend.kafkaconsumer;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import org.reactivestreams.Publisher;

/**
 *
 * @author Ondrej Mihalyi
 */
@ApplicationScoped
public class BtcTxProcessor {

    private FlowableEmitter<String> emitter;
    private Flowable<String> flowable;

    @PostConstruct
    public void init() {
        this.flowable = Flowable.<String>create(
                e -> this.emitter = e,
                BackpressureStrategy.LATEST);
    }

    @Produces
    @ApplicationScoped
    @BtcTx
    public Publisher<String> flowable() {
        return flowable;
    }
    
    @Produces
    @ApplicationScoped
    @BtcTx
    public FlowableEmitter<String> emitter() {
        return emitter;
    }

}
