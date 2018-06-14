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
        flowable = Flowable.<String>
          create(emitter -> {
            this.emitter = emitter;
          }, BackpressureStrategy.DROP);
    }

    public Flowable<String> flowable() {
        return flowable;
    }

    public void emit(@ObservesAsync @BtcTx String data) {
        if (emitter != null) {
            synchronized (emitter) {
                emitter.onNext(data);
            }
        }
    }
}
