package es.eci.elejandria.events.stream.config;

import java.util.HashMap;
import java.util.Map;

import es.eci.elejandria.event.sender.beans.EventBean;
import es.eci.elejandria.events.stream.mapper.CustomerMapper;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class EventStreamConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Autowired
    private CustomerMapper mapper;

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
    public KStream<String, EventBean> eventStream(StreamsBuilder builder) {
        KStream<String, EventBean> stream = builder.stream("events", Consumed.with(Serdes.String(), new JsonSerde<>(EventBean.class)));
        stream.map(mapper).to("events-checked", Produced.with(Serdes.String(), new JsonSerde<>(EventBean.class)));
        return stream;
    }
}
