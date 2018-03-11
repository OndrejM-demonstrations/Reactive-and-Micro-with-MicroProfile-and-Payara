package ondro.btcfrontend.boundary;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import ondro.btcdataproducer.util.Logging;
import ondro.btcfrontend.kafkaconsumer.BtcTxProcessor;

/**
 *
 * @author Ondrej Mihalyi
 */
@Path("btctx")
@RequestScoped
public class BtcTxResource {

    @Inject
    private BtcTxProcessor btcTransactions;

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void getBtcTransactions(
            @Context SseEventSink eventSink,
            @Context Sse sse) {
        OutboundSseEvent.Builder sseDataEventBuilder = sse.newEventBuilder()
                .mediaType(MediaType.APPLICATION_JSON_TYPE);

        btcTransactions.flowable()
                .doOnNext(data -> {
                    Logging.of(this).info("Sending event: " + data);
                    eventSink.send(
                            sseDataEventBuilder
                                    .data(data)
                                    .mediaType(MediaType.APPLICATION_JSON_TYPE)
                                    .build());
                })
                .subscribe();
    }
}
