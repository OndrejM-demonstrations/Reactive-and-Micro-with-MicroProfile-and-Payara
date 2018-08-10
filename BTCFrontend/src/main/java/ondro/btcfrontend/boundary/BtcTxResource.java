package ondro.btcfrontend.boundary;

import java.io.IOException;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;
import ondro.btcdataproducer.util.Logging;
import ondro.btcfrontend.kafkaconsumer.BtcTxProcessor;

/**
 *
 * @author Ondrej Mihalyi
 */
@Path("btctx")
@ApplicationScoped
public class BtcTxResource {

    @Inject
    private BtcTxProcessor btcTransactions;

    private SseBroadcaster btcTxBroadcaster;

    @Context
    private void setSse(Sse sse) {
        this.btcTxBroadcaster = sse.newBroadcaster();
        OutboundSseEvent.Builder sseDataEventBuilder = sse.newEventBuilder()
                .mediaType(MediaType.APPLICATION_JSON_TYPE);
        this.btcTransactions.flowable()
                .doOnNext(data -> {
                    Logging.of(this).info("Sending event: " + data);
                })
                .map(data -> {
                    return sseDataEventBuilder
                            .data(data)
                            .mediaType(MediaType.APPLICATION_JSON_TYPE)
                            .build();
                })
                .doOnError(e -> {
                    Logging.of(this).warning(e.getMessage());
                })
                .doFinally(this.btcTxBroadcaster::close)
                .subscribe(this.btcTxBroadcaster::broadcast);
    }

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void getBtcTransactions(
            @Context SseEventSink eventSink,
            @Context Sse sse) {
        btcTxBroadcaster.register(eventSink);
    }
}
