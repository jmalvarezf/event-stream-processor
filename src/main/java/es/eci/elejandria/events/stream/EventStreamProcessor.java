package es.eci.elejandria.events.stream;

import es.eci.elejandria.event.sender.beans.EventBean;
import es.eci.elejandria.events.stream.mapper.CustomerMapper;
import es.eci.elejandria.events.stream.mapper.PromotionMapper;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;

@EnableBinding(EventStreamProcessor.KStreamProcessorEvents.class)
public class EventStreamProcessor {

    @Autowired
    private CustomerMapper mapper;

    @Autowired
    private PromotionMapper promoMapper;

    @StreamListener("inputEvents")
    @SendTo({"outputEvents"})
    public KStream<String, EventBean>[] eventStream(KStream<String, EventBean> stream) {
        Predicate<String, EventBean> all = (k, v) -> Boolean.TRUE;
        return stream.map(mapper).map(promoMapper).branch(all);
    }

    interface KStreamProcessorEvents {

        @Input("inputEvents")
        KStream<?, ?> inputEvents();

        @Output("outputEvents")
        KStream<?, ?> outputEvents();

    }
}
