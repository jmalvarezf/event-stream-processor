package es.eci.elejandria.events.stream.config;

import es.eci.elejandria.event.sender.beans.EventBean;
import es.eci.elejandria.events.stream.mapper.CustomerMapper;
import es.eci.elejandria.events.stream.mapper.PromotionMapper;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class EventStreamConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public StreamsConfig kStreamsConfigs() {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "event-stream");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, new JsonSerde<>(EventBean.class).getClass());
        props.put(JsonDeserializer.DEFAULT_KEY_TYPE, String.class);
        props.put(JsonDeserializer.DEFAULT_VALUE_TYPE, EventBean.class);
        return new StreamsConfig(props);
    }

    @Bean
    public KStream<String, EventBean> promoStreams(StreamsBuilder builder) {
        KStream<String, EventBean> stream = builder.stream("events-checked", Consumed.with(Serdes.String(), new JsonSerde<>(EventBean.class)));
        Predicate<String, EventBean> hasPromo = (k, v) -> v.getPromotion() != null;
        stream.filter(hasPromo).to("promotions");
        return stream;
    }

}
