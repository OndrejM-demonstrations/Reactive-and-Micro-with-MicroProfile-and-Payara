package ondro.btcfrontend.kafkaconsumer;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.Scheduler;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import ondro.btcfrontend.Resource;

/**
 *
 * @author Ondrej Mihalyi
 */
@ApplicationScoped
public class BtcTxProcessor {

    private FlowableEmitter<String> emitter;
    private Flowable<String> flowable;

    @Inject
    @Resource
    private Scheduler scheduler;

    @PostConstruct
    public void init() {
        flowable = Flowable.<String>
          create(emitter -> {
            this.emitter = emitter;
          }, BackpressureStrategy.DROP)
          .subscribeOn(scheduler)
          .observeOn(scheduler);
    }

    public Flowable<String> flowable() {
        return flowable;
    }

    public void emit(String data) {
        emitter.onNext(data);
    }
}
