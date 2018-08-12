package ondro.btcfrontend.kafkaconsumer;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;

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
        synchronized (this) {
            flowable = Flowable.<String>create(
                    e -> this.emitter = e,
                    BackpressureStrategy.LATEST);
        }
    }

    public Flowable<String> flowable() {
        return flowable;
    }

    public void emit(@ObservesAsync @BtcTx String data) {
        // onNext must be run sequentially and also wait for init() method
        synchronized (this) {
            emitter.onNext(data);
        }
    }
}
